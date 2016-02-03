package com.hc.test.framework.keywords;

import com.hc.test.framework.utils.DriverUtils;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hc.test.framework.core.CustomFunctions;

public class WebKeywords extends CustomFunctions {

	
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

	public boolean chkbox_ischecked(Configuration objRepo, WebDriver driver, String objKey, String data) {
		boolean checkedstatus = true;
		try {
			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
			checkedstatus = driverUtils.getWebElement().isSelected();
			// Thread.sleep(10000);
		} catch (Exception e) {

			checkedstatus = false;
			e.printStackTrace();
		}

		return checkedstatus;

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

	        

}
