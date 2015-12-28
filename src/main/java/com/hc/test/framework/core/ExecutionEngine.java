package com.hc.test.framework.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.hc.test.framework.dao.DataSetDAO;
import com.hc.test.framework.dao.ObjectActionDAO;
import com.hc.test.framework.dao.RunOrderDAO;
import com.hc.test.framework.dao.TestScriptDAO;
import com.hc.test.framework.entities.DataSet;
import com.hc.test.framework.entities.ObjectAction;
import com.hc.test.framework.entities.RunOrder;
import com.hc.test.framework.entities.TestScript;

public class ExecutionEngine {

	@Autowired
	ApplicationContext context;

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(ExecutionEngine.class);
	
	public void mainFlow() {
		RunOrderDAO runOrderDAO = context.getBean(RunOrderDAO.class);

		List<RunOrder> runOrder = runOrderDAO.getExecutableTestCases();

		for (RunOrder runOrderRow : runOrder) {
			TestScriptDAO testScript = context.getBean(TestScriptDAO.class);
			List<TestScript> scriptSteps = testScript
					.getTestScriptStepsByTcId(runOrderRow.getTcId());

			DataSetDAO dataSetDAO = context.getBean(DataSetDAO.class);
			List<DataSet> dataSets = dataSetDAO
					.getExecutableDataSetsByTcId(runOrderRow.getTcId());

			for (DataSet dataSet : dataSets) {

				for (TestScript scriptStep : scriptSteps) {

					ObjectActionDAO objActionDAO = context
							.getBean(ObjectActionDAO.class);
					ObjectAction objAction = objActionDAO
							.getObjectActionById(scriptStep.getObjActionId());
					
					
					
					

				}

			}

		}

	}

}
