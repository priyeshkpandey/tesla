package com.hc.test.framework.keywords;

import com.hc.test.framework.core.CustomFunctions;
import com.hc.test.framework.utils.DriverUtils;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;

public class IOSKeywords extends CustomFunctions {
    DriverUtils driverUtils;

    public boolean click_text(Configuration objRepo, WebDriver driver, String objKey, String data) {
        boolean isClicked=true;

        try {

            driverUtils = new DriverUtils(driver,objRepo,objKey);
            driverUtils.getWebElement().click();


        } catch (Exception e) {

            isClicked = false;
            e.printStackTrace();
        }
        return isClicked;
    }

}
