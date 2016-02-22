package com.hc.test.framework.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hc.test.framework.dao.DataSetDAO;
import com.hc.test.framework.dao.DataSourceDAO;
import com.hc.test.framework.dao.ObjectActionDAO;
import com.hc.test.framework.dao.ResultsDAO;
import com.hc.test.framework.dao.RunOrderDAO;
import com.hc.test.framework.dao.TestScriptDAO;
import com.hc.test.framework.entities.DataSet;
import com.hc.test.framework.entities.DataSource;
import com.hc.test.framework.entities.ObjectAction;
import com.hc.test.framework.entities.Results;
import com.hc.test.framework.entities.RunOrder;
import com.hc.test.framework.entities.TestScript;
import com.hc.test.framework.keywords.ServiceKeywords;
import com.hc.test.framework.selenium.ObjectLocator;
import com.hc.test.framework.selenium.ServerInitializer;

@Component
public class ExecutionEngine {

	@Autowired
	ApplicationContext context;

	@Autowired
	@Qualifier("properties")
	Properties config;
	@Autowired
	ServerInitializer server;

	private WebDriver driver;

	private AppTypes appType;
	
	private String env;
	private Boolean isOnlyAppType;

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory.getLogger(ExecutionEngine.class);

	public void mainFlow(String clientIP, String targetOS,String buildpath) {

		RunOrderDAO runOrderDAO = context.getBean(RunOrderDAO.class);

		List<RunOrder> runOrder = null;
		
		if(isOnlyAppType)
		{
			runOrder = runOrderDAO.getExecutableTestCasesByAppType(appType);
		}
		else
		{
			runOrder = runOrderDAO.getExecutableTestCases();
		}
		
		LOGGER.info("RunOrder rows returned:"+runOrder.size());
		ResultsDAO resultsDAO = context.getBean(ResultsDAO.class);

		for (RunOrder runOrderRow : runOrder) {
			TestScriptDAO testScript = context.getBean(TestScriptDAO.class);
			List<TestScript> scriptSteps = testScript
					.getTestScriptStepsByTcId(runOrderRow.getTcId());
			LOGGER.debug("scriptSteps:"+scriptSteps.size());
			LOGGER.debug("RunOrderRow.getTcId:"+runOrderRow.getTcId());
			DataSetDAO dataSetDAO = context.getBean(DataSetDAO.class);
			List<DataSet> dataSets = dataSetDAO
					.getExecutableDataSetsByTcId(runOrderRow.getTcId());

			LOGGER.info("datasets:"+dataSets.size());
			String objRepoName = runOrderRow.getObjRepoName();
			ObjectLocator objLoc = new ObjectLocator(objRepoName);
			Configuration objRepo = objLoc.getObjectsFromRepo();

			try {
				appType = runOrderRow.getAppType();
				server.setExecutionPlatform(appType.appName());
				server.setServerurl("http://"+clientIP);
				server.setTargetOs(targetOS);
				server.setBuildpath(buildpath);
				driver = server.getDriver();

				HashMap<Long, Integer> stepsCounts = new HashMap<Long, Integer>();

				for (TestScript step : scriptSteps) {
					stepsCounts.put(step.getStepSeq(), 0);
				}

				for (DataSet dataSet : dataSets) {

					ListIterator<TestScript> listIterTS = scriptSteps
							.listIterator();
					
					Boolean tcStatus = true;

					stepsLoop: while (listIterTS.hasNext()) {
						LOGGER.info("ListIterTs:");
						TestScript scriptStep = listIterTS.next();
						

						ObjectActionDAO objActionDAO = context
								.getBean(ObjectActionDAO.class);
						ObjectAction objAction = objActionDAO
								.getObjectActionById(scriptStep
										.getObjActionId());

						DataSourceDAO dataSourceDAO = context.getBean(DataSourceDAO.class);
						System.out.println("dataset id:"+dataSet.getDataSetId());
						DataSource dataSource = dataSourceDAO.getDataIdByDataSetIdAndStepSeq(dataSet.getDataSetId(),scriptStep.getStepSeq());

						// TODO: Step invocation

						KeywordInvoker invoker = new KeywordInvoker(appType);
						String keyword = objAction.getAction();
						String objKey = objAction.getScreenName() + "."
								+ objAction.getObjName();

						ArrayList<Object> params = new ArrayList<Object>();
						params.add(objRepo);
//						params.add(driver);
						params.add(objKey);
						params.add(dataSource.getValue());

						boolean result = invoker.invokeKeyword(keyword, params);

						Integer stepCount = stepsCounts.get(scriptStep
								.getStepSeq());
						stepCount++;
						stepsCounts.put(scriptStep.getStepSeq(), stepCount);

						try {

							String stepLoop = null;

							if (result) {
								stepLoop = scriptStep.getOnPass();
							} else {
								stepLoop = scriptStep.getOnFail();
							}

							int stepLoopCount = 1;

							if (stepLoop != null && stepLoop.contains("loop=")) {
								String[] refColPairs = stepLoop.split(";");

								for (String refPair : refColPairs) {
									String key = refPair.split("=")[0];
									String val = refPair.split("=")[1];

									if (key.equalsIgnoreCase("loop")) {
										stepLoopCount = Integer.parseInt(val);
									}

								}
							}

							if (stepCount >= stepLoopCount) {
								throw new NoJumpStepException();
							}

							Long jumpStep = stepResultHandler(result,
									scriptSteps, scriptStep);

							if (jumpStep == null) {
								throw new NoJumpStepException();
							} else {
								TestScript targetStep = scriptSteps
										.get(jumpStep.intValue());
								listIterTS.set(targetStep);
								if (listIterTS.hasPrevious()) {
									listIterTS.previous();
								}

							}
						} catch (IOException e) {
							LOGGER.error("Step " + scriptStep.getStepSeq()
									+ " failed for the test script "
									+ scriptStep.getTcId()
									+ " due to exception " + e.getMessage());
							tcStatus = false;
							logFailedResult(scriptStep,dataSet.getDataSetId(),resultsDAO);
							break stepsLoop;
						} catch (NoJumpStepException e) {
							LOGGER.info(e.getMessage());
							if (!result) {
								LOGGER.error("Step " + scriptStep.getStepSeq()
										+ " failed for the test script "
										+ scriptStep.getTcId());
								tcStatus = false;
								logFailedResult(scriptStep,dataSet.getDataSetId(),resultsDAO);
								break stepsLoop;
							}
						}
						
						

					}
					
					if(tcStatus)
					{
						logPassedResult(runOrderRow.getTcId(),dataSet.getDataSetId(),resultsDAO);
					}

				}

			} catch (MalformedURLException e) {
				LOGGER.error(e.getMessage());
			}

		}

	}

	private Long stepResultHandler(boolean result, List<TestScript> testScript,
			TestScript step) throws IOException {

		String label = null;
		String ref = null;
		Long jumpStep = null;
		if (result) {
			ref = step.getOnPass();
			onPassHandler(step);
		} else {
			ref = step.getOnFail();
			onFailHandler(step);
		}

		if (ref != null && ref.contains("ref=")) {
			String[] refColPairs = ref.split(";");

			for (String refPair : refColPairs) {
				String key = refPair.split("=")[0];
				String val = refPair.split("=")[1];

				if (key.equalsIgnoreCase("ref")) {
					label = val;
				}

			}
		}

		if (label != null && !label.equalsIgnoreCase("")) {
			jumpStep = getStepSeqFromLabel(label, testScript, result);
		}

		return jumpStep;

	}

	private Long getStepSeqFromLabel(String label, List<TestScript> testScript,
			Boolean isPass) {
		Long stepSeq = null;

		for (TestScript step : testScript) {
			String ref = null;
			if (isPass) {
				ref = step.getOnPass();
			} else {
				ref = step.getOnFail();
			}

			if (ref!= null && ref.contains("label=")) {
				String[] refColPairs = ref.split(";");

				for (String refPair : refColPairs) {
					String key = refPair.split("=")[0];
					String val = refPair.split("=")[1];

					if (key.equalsIgnoreCase("label")
							&& val.equalsIgnoreCase(label)) {
						stepSeq = step.getStepSeq();
					}

				}
			}

		}

		return stepSeq;
	}

	private void onPassHandler(TestScript step) throws IOException {

		if (step.getIsScreenshot()) {
			takeScreenshot(step);
		}
	}

	private void onFailHandler(TestScript step) throws IOException {

		takeScreenshot(step);
	}
	
	@Transactional
	private void logFailedResult(TestScript step, Long dataSetId, ResultsDAO resultsDAO)
	{
		Results result = new Results();
		result.setTcId(step.getTcId());
		result.setStepSeq(step.getStepSeq());
		result.setDataSetId(dataSetId);
		result.setStatus(0);
		result.setCodeDrop(config.getProperty("codeDrop"));
		result.setRelease(config.getProperty("release"));
		result.setTestPhase(config.getProperty("testPhase"));
		result.setExecutedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
		result.setEnv(env);
		resultsDAO.save(result);
	}
	
	@Transactional
	private void logPassedResult(Long tcId, Long dataSetId, ResultsDAO resultsDAO)
	{
		Results result = new Results();
		result.setTcId(tcId);
		result.setDataSetId(dataSetId);
		result.setStatus(1);
		result.setCodeDrop(config.getProperty("codeDrop"));
		result.setRelease(config.getProperty("release"));
		result.setTestPhase(config.getProperty("testPhase"));
		result.setExecutedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
		result.setEnv(env);
		resultsDAO.save(result);
	}

	private void takeScreenshot(TestScript step) throws IOException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

		MessageFormat fileName = new MessageFormat(
				config.getProperty("screenshotLocation"));
		Object[] formatParams = {
				dateFormat.format(Calendar.getInstance().getTime()),
				step.getTcId(), step.getStepSeq(),
				timeStamp.format(Calendar.getInstance().getTime()) };

		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(fileName.format(formatParams)));
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	

	public Boolean getIsOnlyAppType() {
		return isOnlyAppType;
	}

	public void setIsOnlyAppType(Boolean isOnlyAppType) {
		this.isOnlyAppType = isOnlyAppType;
	}

	public AppTypes getAppType() {
		return appType;
	}

	public void setAppType(AppTypes appType) {
		this.appType = appType;
	}
	
	
}
