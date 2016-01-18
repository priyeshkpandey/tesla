package com.hc.test.framework.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeywordInvoker {

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory.getLogger(KeywordInvoker.class);
	
	private AppTypes appType;
	
	KeywordInvoker(AppTypes appType)
	{
		this.appType = appType;
	}

	public boolean invokeKeyword(String keyword, List<Object> params) {
		boolean isInvoked = true;

		try {
			Class c = Class.forName("com.hc.test.framework.keywords."
					+ appType.keywordClass());
			
			if(keyword.equalsIgnoreCase("CustomFunction"))
			{
				String customParams = (String)params.get(3);
				String customFuncName = customParams.split("(")[0];
				String[] customParamsArray = customParams.split("(")[1].split(",");
				int noOfParams = customParamsArray.length;
				customParamsArray[noOfParams-1] = customParamsArray[noOfParams-1].replace(")", " ").trim();
				Class[] paramTypes = new Class[noOfParams+2];
				List<Object> customParamsVal = new ArrayList<Object>();
				paramTypes[0] = Configuration.class;
				paramTypes[1] = WebDriver.class;
				int nextIndx = 2;
				customParamsVal.add(params.get(0));
				customParamsVal.add(params.get(1));
				
				
				for(String param:customParamsArray)
				{
					paramTypes[nextIndx] = String.class;
					customParamsVal.add(param);
				}
				
				Method customMethod = c.getDeclaredMethod(customFuncName, paramTypes);
				isInvoked = (boolean)customMethod.invoke(c.newInstance(), customParamsVal);
			}
			else
			{
			Class[] paramTypes = { Configuration.class, WebDriver.class,
					String.class, String.class };
			
			Method keywordMethod = c.getDeclaredMethod(keyword, paramTypes);
			isInvoked = (boolean) keywordMethod.invoke(c.newInstance(), params.toArray());
			}

		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
			isInvoked = false;
		} catch (NoSuchMethodException e) {
			LOGGER.error(e.getMessage());
			isInvoked = false;
		} catch (SecurityException e) {
			LOGGER.error(e.getMessage());
			isInvoked = false;
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage());
			isInvoked = false;
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			isInvoked = false;
		} catch (InvocationTargetException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			isInvoked = false;
		} catch (InstantiationException e) {
			LOGGER.error(e.getMessage());
			isInvoked = false;
		}

		return isInvoked;
	}

}
