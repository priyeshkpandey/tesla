package com.hc.test.framework.keywords;

import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hc.test.framework.core.CustomFunctions;

public class WebKeywords extends CustomFunctions {

	
	public boolean input(Configuration objRepo, WebDriver driver, String objKey, String data) {
		boolean isSuccess = true;
		try {
			String locator=objRepo.getString(objKey);
			String locatorType =locator.split("=")[0];
			String locatorValue = locator.split("=")[1];
			WebElement element = driver.findElement(getLocator(locatorType, locatorValue));
			element.sendKeys(data);
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

	private By getLocator(String locatorType, String locatorValue) {
		By by = null;
		switch (locatorType.toLowerCase()) {
		case "name":
			by = By.name(locatorValue);
			break;
		case "xpath":
			by = By.xpath(locatorValue);
			break;

		}
		return by;
	}
	        

}
