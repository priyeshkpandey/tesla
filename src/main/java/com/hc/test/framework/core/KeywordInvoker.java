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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hc.test.framework.keywords.ServiceKeywords;

@Component
public class KeywordInvoker {

	@Autowired
	CustomFunctions customFunctions;
	
	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory.getLogger(KeywordInvoker.class);

	public AppTypes getAppType() {
		return appType;
	}

	public void setAppType(AppTypes appType) {
		this.appType = appType;
	}

	private AppTypes appType;
	
	KeywordInvoker()
	{
		
	};


	KeywordInvoker(AppTypes appType)
	{
		this.appType = appType;
	}

	public boolean invokeKeyword(String keyword, List<Object> params) {
		boolean isInvoked = true;

		try {			
			if(keyword.equalsIgnoreCase("CustomFunction"))
			{
				
//				c =customFunctions.getClass();
				String customParams = (String)params.get(2);
				
				String customFuncName = customParams.split("\\(")[0];
				String[] customParamsArray;
				String test=customParams.split("\\(")[1].replace("\\)","" );
				customParamsArray = customParams.split("\\(")[1].split(",");
				
				int noOfParams = customParamsArray.length;
				customParamsArray[noOfParams-1] = customParamsArray[noOfParams-1].replace("\\)", " ").trim();
				Class[] paramTypes = new Class[noOfParams];
//				Class[] paramTypes = new Class[noOfParams+2];
				List<Object> customParamsVal = new ArrayList<Object>();
//				paramTypes[0] = Configuration.class;
//				paramTypes[1] = WebDriver.class;
				int nextIndx = 0;
//				customParamsVal.add(params.get(0));
//				customParamsVal.add(params.get(1));
				
				for(String param:customParamsArray)
					
				{
					paramTypes[nextIndx] = String.class;
					if(param.contains(")")){
						int len=param.length();
						param=param.substring(0, len-1);
					}
					customParamsVal.add(param);
					nextIndx++;
				}
				
				Method customMethod = customFunctions.getClass().getMethod(customFuncName, paramTypes);
				isInvoked = (boolean)customMethod.invoke(customFunctions, customParamsVal.toArray());
			}
			else
			{
				Class c = Class.forName("com.hc.test.framework.keywords."
						+ getAppType().keywordClass());
			Class[] paramTypes = { Configuration.class, WebDriver.class,
					String.class, String.class };
			
			Method keywordMethod = c.getDeclaredMethod(keyword, paramTypes);
			isInvoked = (boolean) keywordMethod.invoke(c.newInstance(), params.toArray());
			}

//		} catch (ClassNotFoundException e) {
//			LOGGER.error(e.getMessage());
//			isInvoked = false;
//		}
		}catch (NoSuchMethodException e) {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isInvoked;
	}

}
