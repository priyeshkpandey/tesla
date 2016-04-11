package com.hc.test.framework.keywords;

import com.hc.test.framework.utils.DriverUtils;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	 public boolean openUrl(Configuration objRepo, WebDriver driver,String objKey, String data) {
	        boolean isLaunched = true;
	        try {
	            
	            driver.get(data);
	        } catch (Exception e) {
	            isLaunched = false;
	            e.printStackTrace();
	        }
	        return isLaunched;
	    }
	 
	 public boolean waitForPresenceofElement(Configuration objRepo, WebDriver driver, String objKey, String data)
	 {
		 boolean isSucess = true;
	   		try {
	   			DriverUtils driverUtils=new DriverUtils(driver,objRepo,objKey);
	   			WebDriverWait wait = new WebDriverWait(driver, 60);
	   			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(driverUtils.getLocator()));
	   		} catch (Exception e) {

	   			isSucess = false;
	   			e.printStackTrace();
	   		}

	   		return isSucess;
	 }

	 public boolean cleartext(Configuration objRepo, WebDriver driver, String objKey, String data)
	 {
		 boolean isCleared = true;
	   		try {
	   			Thread.sleep(10000);
	   			DriverUtils driverUtils=new DriverUtils(driver,objRepo,objKey);
	   			driverUtils.getWebElement().clear();
	   		} catch (Exception e) {

	   			isCleared = false;
	   			e.printStackTrace();
	   		}

	   		return isCleared;
	 }
	       public boolean click(Configuration objRepo, WebDriver driver, String objKey, String data) {
	    	   boolean isSucess = true;
	    	   
	   		try {
	   			Thread.sleep(5000);
	   			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
	   			driverUtils.getWebElement().click();
	   			// Thread.sleep(10000);
	   		} catch (Exception e) {

	   			isSucess = false;
	   			e.printStackTrace();
	   		}

	   		return isSucess;
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

	public boolean alertaccept(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
	   		try {
	   			Thread.sleep(10000);
	   			Alert alt = driver.switchTo().alert();
	   			alt.accept();
	   		
	   			// Thread.sleep(10000);
	   		} catch (Exception e) {

	   			isSucess = false;
	   			e.printStackTrace();
	   		}

	   		return isSucess;
	}
	

}
