package com.hc.test.framework.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.hc.test.framework.dao.DataSetDAO;
import com.hc.test.framework.dao.DataSourceDAO;
import com.hc.test.framework.dao.ObjectActionDAO;
import com.hc.test.framework.dao.RunOrderDAO;
import com.hc.test.framework.dao.TestScriptDAO;
import com.hc.test.framework.entities.DataSet;
import com.hc.test.framework.entities.DataSource;
import com.hc.test.framework.entities.ObjectAction;
import com.hc.test.framework.entities.RunOrder;
import com.hc.test.framework.entities.TestScript;
import com.hc.test.framework.selenium.ObjectLocator;
import com.hc.test.framework.selenium.ServerInitializer;

public class ExecutionEngine {

	@Autowired
	ApplicationContext context;

	@Autowired
	@Qualifier("properties")
	Properties config;

	private WebDriver driver;

	private AppTypes appType;

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(ExecutionEngine.class);

	public void mainFlow(String clientIP, String targetOS) {

		RunOrderDAO runOrderDAO = context.getBean(RunOrderDAO.class);

		List<RunOrder> runOrder = runOrderDAO.getExecutableTestCases();

		for (RunOrder runOrderRow : runOrder) {
			TestScriptDAO testScript = context.getBean(TestScriptDAO.class);
			List<TestScript> scriptSteps = testScript
					.getTestScriptStepsByTcId(runOrderRow.getTcId());

			DataSetDAO dataSetDAO = context.getBean(DataSetDAO.class);
			List<DataSet> dataSets = dataSetDAO
					.getExecutableDataSetsByTcId(runOrderRow.getTcId());

			String objRepoName = runOrderRow.getObjRepoName();
			ObjectLocator objLoc = new ObjectLocator(objRepoName);
			Configuration objRepo = objLoc.getObjectsFromRepo();

			try {
				appType = runOrderRow.getAppType();
				ServerInitializer server = new ServerInitializer("http://"
						+ clientIP, appType.appName(), targetOS);
				driver = server.getDriver();

				for (DataSet dataSet : dataSets) {

					ListIterator<TestScript> listIterTS = scriptSteps
							.listIterator();

					stepsLoop: while (listIterTS.hasNext()) {

						TestScript scriptStep = listIterTS.next();

						ObjectActionDAO objActionDAO = context
								.getBean(ObjectActionDAO.class);
						ObjectAction objAction = objActionDAO
								.getObjectActionById(scriptStep
										.getObjActionId());

						DataSourceDAO dataSourceDAO = context
								.getBean(DataSourceDAO.class);
						DataSource dataSource = dataSourceDAO
								.getDataIdByDataSetIdAndStepSeq(
										dataSet.getDataSetId(),
										scriptStep.getStepSeq());

						// TODO: Step invocation

						KeywordInvoker invoker = new KeywordInvoker(appType);
						String keyword = objAction.getAction();
						String objKey = objAction.getScreenName() + "."
								+ objAction.getObjName();

						ArrayList params = new ArrayList();
						params.add(objRepo);
						params.add(driver);
						params.add(objKey);
						params.add(dataSource.getValue());
						
						boolean result = invoker.invokeKeyword(keyword, params);
						
						try {
							Long jumpStep = stepResultHandler(result, scriptSteps, scriptStep);
							if(jumpStep == null)
							{
								throw new NoJumpStepException();
							}
							else
							{
								TestScript targetStep = scriptSteps.get(jumpStep.intValue());
								listIterTS.set(targetStep);
								if(listIterTS.hasPrevious())
								{
									listIterTS.previous();
								}
									
							}
						} catch (IOException e) {
							LOGGER.error("Step "+scriptStep.getStepSeq()+" failed for the test script "+scriptStep.getId());
							break stepsLoop;
						} catch (NoJumpStepException e) {
							LOGGER.info(e.getMessage());
							if(!result)
							{
								break stepsLoop;
							}
						}
						

					}

				}

			} catch (MalformedURLException e) {
				LOGGER.error(e.getMessage());
			}

		}

	}

	private Long stepResultHandler(boolean result, List<TestScript> testScript, TestScript step) throws IOException {
		
		String label = null;
		String ref = null;
		Long jumpStep = null;
		if(result)
		{
			ref = step.getOnPass();
			onPassHandler(step);
		}
		else
		{
			ref = step.getOnFail();
			onFailHandler(step);
		}
		
		if(ref.contains("ref="))
		{
			String[] refColPairs = ref.split(";");
			
			for(String refPair:refColPairs)
			{
				String key = refPair.split("=")[0];
				String val = refPair.split("=")[1];
				
				if(key.equalsIgnoreCase("ref") )
				{
					label = val;
				}
				
			}
		}
		
		if(label!=null && !label.equalsIgnoreCase(""))
		{
			jumpStep = getStepSeqFromLabel(label, testScript, result);
		}
		
		return jumpStep;

	}
	
	private Long getStepSeqFromLabel(String label, List<TestScript> testScript, Boolean isPass)
	{
		Long stepSeq = null;
		
		for(TestScript step:testScript)
		{
			String ref = null;
			if(isPass)
			{
				ref = step.getOnPass();
			}
			else
			{
				ref = step.getOnFail();
			}
			
			if(ref.contains("label="))
			{
				String[] refColPairs = ref.split(";");
				
				for(String refPair:refColPairs)
				{
					String key = refPair.split("=")[0];
					String val = refPair.split("=")[1];
					
					if(key.equalsIgnoreCase("label") && val.equalsIgnoreCase(label))
					{
						stepSeq = step.getStepSeq();
					}
					
				}
			}		
				
		}
		
		return stepSeq;
	}

	private void onPassHandler(TestScript step) throws IOException {

		
		if(step.getIsScreenshot())
		{
			takeScreenshot(step);
		}
	}

	private void onFailHandler(TestScript step) throws IOException {
		
		
		takeScreenshot(step);
	}
	
	private void takeScreenshot(TestScript step) throws IOException
	{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		
		MessageFormat fileName = new MessageFormat(config.getProperty("screenshotLocation"));
		Object [] formatParams = {dateFormat.format(Calendar.getInstance().getTime()),
				step.getId(), step.getStepSeq(),
				timeStamp.format(Calendar.getInstance().getTime())};
		
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
           FileUtils.copyFile(scrFile, new File(fileName.format(formatParams)));
	}
}
