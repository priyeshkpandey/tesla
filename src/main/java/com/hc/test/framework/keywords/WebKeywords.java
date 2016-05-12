package com.hc.test.framework.keywords;

import com.hc.test.framework.utils.Constants;
import com.hc.test.framework.utils.DriverUtils;



import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

	
	public boolean enterText(Configuration objRepo, WebDriver driver, String objKey, String data) {

		boolean isSuccess = true;
		LOGGER.info("keyword enterText started executing");
		try {
			Thread.sleep(Constants.THREAD_SLEEP);
			DriverUtils driverUtils=new DriverUtils(driver,objRepo,objKey);
            driverUtils.getWebElement().sendKeys(data);
		} catch (Exception e) {
			
			isSuccess = false;
			ExceptionUtils.getStackTrace(e);
			e.printStackTrace();
		}
		LOGGER.info("keyword enterText completed executing");
		return isSuccess;
	}

	 public boolean openUrl(Configuration objRepo, WebDriver driver,String objKey, String data) {
	        boolean isLaunched = true;
	        LOGGER.info("keyword openUrl started executing");
	        try {
	        	Thread.sleep(Constants.THREAD_SLEEP);
	            driver.get(data);
	        } catch (Exception e) {
	            isLaunched = false;
	            ExceptionUtils.getStackTrace(e);
	            e.printStackTrace();
	        }
	        LOGGER.info("keyword openUrl completed executing");
	        return isLaunched;
	    }
	 
	 public boolean waitForPresenceofElement(Configuration objRepo, WebDriver driver, String objKey, String data)
	 {
		 boolean isSucess = true;
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   			DriverUtils driverUtils=new DriverUtils(driver,objRepo,objKey);
	   			WebDriverWait wait = new WebDriverWait(driver, 60);
	   			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(driverUtils.getLocator()));
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}

	   		return isSucess;
	 }

	 public boolean cleartext(Configuration objRepo, WebDriver driver, String objKey, String data)
	 {
		 boolean isCleared = true;
		 LOGGER.info("keyword clear Text started executing");
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   			DriverUtils driverUtils=new DriverUtils(driver,objRepo,objKey);
	   			driverUtils.getWebElement().clear();
	   		} catch (Exception e) {

	   			isCleared = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   		LOGGER.info("keyword Clear Text completed executing");

	   		return isCleared;
	 }
	       public boolean click(Configuration objRepo, WebDriver driver, String objKey, String data) {
	    	   boolean isSucess = true;
	    	   LOGGER.info("keyword click started executing");
	    	   
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
	   			driverUtils.getWebElement().click();
	   			
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   	 LOGGER.info("keyword click completed executing");
	   		return isSucess;
	   	}
	       
	public boolean chkbox_click(Configuration objRepo, WebDriver driver, String objKey, String data) {
		boolean checkstatus = true;
		 LOGGER.info("keyword chbox_click started executing");
		try {
			Thread.sleep(Constants.THREAD_SLEEP);
			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
			driverUtils.getWebElement().click();
			
		} catch (Exception e) {

			checkstatus = false;
			ExceptionUtils.getStackTrace(e);
			e.printStackTrace();
		}
		 LOGGER.info("keyword chbox_click completed executing");
		return checkstatus;
	}


	public boolean chkbox_ischecked(Configuration objRepo, WebDriver driver, String objKey, String data) {
		boolean checkedstatus = true;
		try {
			Thread.sleep(Constants.THREAD_SLEEP);
			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
			checkedstatus = driverUtils.getWebElement().isSelected();
	
		} catch (Exception e) {

			checkedstatus = false;
			ExceptionUtils.getStackTrace(e);
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
			ExceptionUtils.getStackTrace(e);
			e.printStackTrace();
		}

		return isClosed;
	}


	public boolean alertaccept(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
		 LOGGER.info("keyword Alertaccept executing");
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   			Alert alt = driver.switchTo().alert();
	   			alt.accept();
	   		
	   			// Thread.sleep(10000);
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   	 LOGGER.info("keyword Alertaccept completed  executing");
	   		return isSucess;
	   		

	}
	

	public boolean mouseClick(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
		 LOGGER.info("keyword mouseClick started executing");
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   			Actions actions = new Actions(driver);
	   			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
	   			WebElement menuoptions = driverUtils.getWebElement();
	   			actions.moveToElement(menuoptions);
	   			actions.click().build().perform();
	   			
	   		
	   			
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   	 LOGGER.info("keyword mouseClick completed  executing");
	   		return isSucess;
	}
	
	public boolean verifyText(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
		 LOGGER.info("keyword veifyText started executing");
		 String actualtext =null;
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
	   			actualtext =  driverUtils.getWebElement().getText();
	   			if(!actualtext.equals(data))
	   			{
	   				isSucess = false;
	   			}
	   		
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   	 LOGGER.info("keyword Verify Text completed executing");
	   		return isSucess;
	}
	
	public boolean verifyTitle(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
		 LOGGER.info("keyword VerifyTitle started executing");
		 String actualtext =null;
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   			actualtext =  driver.getTitle();
	   			if(!actualtext.equals(data))
	   			{
	   				isSucess = false;
	   			}
	   		
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   	 LOGGER.info("keyword Verify Title completed executing");
	   		return isSucess;
	}
	
	public boolean waitforsometime(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
		
	   		try {
	   			Thread.sleep(1000);
	   			
	   		
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   		return isSucess;
	}
	
	public boolean key_down_enter(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
		
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   			Actions actions = new Actions(driver);
	   			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
	   			actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
	   		
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   		return isSucess;
	}
	
	public boolean isAlertPresent(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
		
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   			DriverUtils driverUtils=new DriverUtils(driver,objRepo,objKey);
	   			WebDriverWait wait = new WebDriverWait(driver, 60);
	   			if(wait.until(ExpectedConditions.alertIsPresent())==null){
	   				isSucess = false;
	   			}
	   	
	   		
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   		return isSucess;
	}
	
	public boolean key_enter(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
		
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   	
	   			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
	   			driverUtils.getWebElement().sendKeys(Keys.ENTER);
	   		
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   		return isSucess;
	}
	
	
	public boolean scrolldown(Configuration objRepo, WebDriver driver, String objKey, String data) {
		 boolean isSucess = true;
		
	   		try {
	   			Thread.sleep(Constants.THREAD_SLEEP);
	   	
	   			DriverUtils driverUtils = new DriverUtils(driver, objRepo, objKey);
	   			WebElement ele = driverUtils.getWebElement();
	   			ele.click();
	   			ele.click();
	   			for (int i=0;i<Integer.parseInt(data);i++)
	   			{
	   				ele.sendKeys(Keys.ARROW_DOWN);
	   			}
	   		
	   		} catch (Exception e) {

	   			isSucess = false;
	   			ExceptionUtils.getStackTrace(e);
	   			e.printStackTrace();
	   		}
	   		return isSucess;
	}
}
