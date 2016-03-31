package com.hc.test.framework.keywords;

import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;

import com.hc.test.framework.core.CustomFunctions;
import com.hc.test.framework.utils.DriverUtils;

public class AndroidKeywords extends CustomFunctions {
	
    DriverUtils driverUtils;

    public boolean tapOnTile(Configuration objRepo, WebDriver driver, String objKey, String data) {
        boolean isClicked=false;

        try {

            driverUtils = new DriverUtils(driver,objRepo,objKey);
            driverUtils.getWebElement().click();
            isClicked=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isClicked;
    }
}
