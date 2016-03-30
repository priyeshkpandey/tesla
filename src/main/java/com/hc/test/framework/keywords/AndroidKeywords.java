package com.hc.test.framework.keywords;

import com.hc.test.framework.core.CustomFunctions;
import com.hc.test.framework.utils.DriverUtils;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AndroidKeywords extends CustomFunctions {

    DriverUtils driverUtils;


    public boolean enterText(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean isLoggedin=true;
        try {
            Thread.sleep(5);
            driverUtils=new DriverUtils(driver, objRepo, objKey);
            WebElement element=driverUtils.getWebElement();
            element.clear();
            element.sendKeys(data);

        }catch (Exception e){
            isLoggedin=false;
        }
        return isLoggedin;
    }

    public boolean tapOnButton(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean isLoggedin=true;
        try {
            Thread.sleep(5);
            driverUtils= new DriverUtils(driver, objRepo, objKey);
            WebElement element=driverUtils.getWebElement();
            element.click();

        }catch (Exception e){
            isLoggedin=false;
        }
        return isLoggedin;
    }

    public boolean clearText(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean isCleared=true;
        try {
            Thread.sleep(5);
            driverUtils=new DriverUtils(driver, objRepo, objKey);
            driverUtils.getWebElement().clear();
            ;

        }catch (Exception e){
            isCleared=false;
        }
        return isCleared;
    }
}
