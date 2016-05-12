package com.hc.test.framework.keywords;

import com.hc.test.framework.utils.DriverUtils;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Pattern;

import com.hc.test.framework.core.CustomFunctions;
import com.hc.test.framework.core.KeywordInvoker;

public class WebKeywords extends CustomFunctions {

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory.getLogger(WebKeywords.class);

	public boolean input(Configuration objRepo, WebDriver driver, String objKey, String data) {
		boolean isSuccess = true;
		try {
			DriverUtils driverUtils=new DriverUtils(driver,objRepo,objKey);
            driverUtils.getWebElement().sendKeys(data);
		} catch (Exception e) {
			
			isSuccess = false;
			e.printStackTrace();
		}

		return isSuccess;
	}

	       public boolean openUrl(Configuration objRepo, WebDriver driver,
	            String objKey, String data) {
	        boolean isLaunched = true;
	        try {
	            
	            driver.get(data);
	        } catch (Exception e) {
	            isLaunched = false;
	            e.printStackTrace();
	        }
	        return isLaunched;
	    }

	public boolean chkbox_click(Configuration objRepo, WebDriver driver, String objKey, String data) {
		boolean checkstatus = true;
		try {
			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
			driverUtils.getWebElement().click();
			// Thread.sleep(10000);
		} catch (Exception e) {

			checkstatus = false;
			e.printStackTrace();
		}

		return checkstatus;
	}

	
	   	
	public boolean closebrowser(Configuration objRepo, WebDriver driver, String objKey, String data) {
		boolean isClosed = true;
		try {
			driver.close();
			driver.quit();
		} catch (Exception e) {

			isClosed = false;
			e.printStackTrace();
		}

		return isClosed;
	}

	/**
	 * This keyword will be used to verify the text of an object 
	 * @param objRepo
	 * @param driver
	 * @param objKey
	 * @param data - complete text which need to be verified
	 * @return
	 */
	public boolean verifyText(Configuration objRepo, WebDriver driver, String objKey, String data) {
		boolean checkstatus = true;
		try {
			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
			WebElement ele = driverUtils.getWebElement();
			if(ele.getText().equals(data)){
				LOGGER.debug("Pass","Actual Value = "+ele.getText()
						+"; Expected Value ="+data);
			}
			else{
				LOGGER.debug("Fail","Actual Value = "+ele.getText()
				+"; Expected Value ="+data);
				
				checkstatus = false;
			}
			
		} catch (Exception e) {

			checkstatus = false;
			e.printStackTrace();
		}

		return checkstatus;
	}
	
	
	/**
	 * This keyword will be used to verify the regular exp with an object's text 
	 * @param objRepo
	 * @param driver
	 * @param objKey
	 * @param data - complete text which need to be verified
	 * @return
	 */
	public boolean verifyRegEx(Configuration objRepo, WebDriver driver, String objKey, String data) {
		boolean checkstatus = true;
		try {
			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
			WebElement ele = driverUtils.getWebElement();
			String objText = ele.getText();
			
			
			
			if(Pattern.matches(data, objText)){
				LOGGER.debug("Pass","Actual Value = "+ele.getText()
						+"; Expected Value ="+data);
			}
			else{
				LOGGER.debug("Fail","Actual Value = "+ele.getText()
				+"; Expected Value ="+data);
				
				checkstatus = false;
			}
			
		} catch (Exception e) {

			checkstatus = false;
			e.printStackTrace();
		}

		return checkstatus;
	}
	

}
