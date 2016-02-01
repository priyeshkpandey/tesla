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
            driverUtils.getWeElement().sendKeys(data);
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

	        

}
