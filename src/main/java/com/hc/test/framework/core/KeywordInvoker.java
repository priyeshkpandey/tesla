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
				
				c = Class.forName("com.hc.test.framework.keywords."
						+ appType.keywordClass());
				String customParams = (String)params.get(2);
				
				System.out.println("CustomParams:"+params.get(1));
				String customFuncName = customParams.split("\\(")[0];
				String[] customParamsArray;
				String test=customParams.split("\\(")[1].replace("\\)","" );
				System.out.println("Tested:"+test);
				customParamsArray = customParams.split("\\(")[1].split(",");
				
				int noOfParams = customParamsArray.length;
				customParamsArray[noOfParams-1] = customParamsArray[noOfParams-1].replace("\\)", " ").trim();
				Class[] paramTypes = new Class[noOfParams];
				List<Object> customParamsVal = new ArrayList<Object>();
//				paramTypes[0] = Configuration.class;
//				paramTypes[1] = WebDriver.class;
				int nextIndx = 0;
//				customParamsVal.add(params.get(0));
//				customParamsVal.add(params.get(1));
				
				for(String param:customParamsArray)
					
				{
					paramTypes[nextIndx] = String.class;
					customParamsVal.add(param);
					nextIndx++;
				}
				Method customMethod = c.getMethod(customFuncName, paramTypes);
				System.out.println("CustomParamVals size :"+customParamsVal.size());
//				System.out.println("CustomParamVals:"+customParamsVal.get(3));
//				customParamsVal.add(2, "http://172.16.1.113:8080/masterdata/v4/zip");
//				customParamsVal.add(3, "zipcode=560037");
				
//System.out.println("customMethod"+customMethod.getParameterTypes().toString());
//System.out.println("customMethod"+customMethod.getName().toString());
//System.out.println("customMethod"+customMethod.getGenericParameterTypes().getClass().getCanonicalName());


				isInvoked = (boolean)customMethod.invoke(c.newInstance(), customParamsVal.toArray());
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
			e.printStackTrace();
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
