package com.hc.test.framework.core;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
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

	public void mainFlow(String clientIP) {

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
						+ clientIP, appType.appName());
				driver = server.getDriver();

				for (DataSet dataSet : dataSets) {

					ListIterator<TestScript> listIterTS = scriptSteps
							.listIterator();

					while (listIterTS.hasNext()) {

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

					}

				}

			} catch (MalformedURLException e) {
				LOGGER.error(e.getMessage());
			}

		}

	}

	private void stepResultHandler(boolean result) {

	}

	private void onPassHandler() {

	}

	private void onFailHandler() {

	}
}
