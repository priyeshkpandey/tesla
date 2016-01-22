package com.hc.test.framework.utils;

import com.google.common.base.Function;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DriverUtils {
    public WebDriver webDriver;
    public Configuration objRepo;
    public String objKey;
    public By by;
    private boolean shouldCache=false;
    String locator;
    WebElement element;
    List<WebElement> elementList;
    AndroidDriver<?> androidDriver;
    IOSDriver<?> iosDriver;
    private AppiumDriver<?> appiumDriver;

    public DriverUtils(WebDriver webDriver, Configuration objRepo, String objKey) {
        this.webDriver = webDriver;
        this.objKey = objKey;
        this.objRepo = objRepo;
        locator = objRepo.getString(objKey);

    }

    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
    }

    public static Logger LOGGER = LoggerFactory.getLogger(DriverUtils.class);

    public By getLocator() {

        String locatorArray[] = locator.split("=");

        switch (locatorArray[0].toLowerCase()) {
            case "name":
                by = By.name(locatorArray[1]);
                break;
            case "xpath":
                by = By.xpath(locatorArray[1]);
                break;
            case "id":
                by = By.id(locatorArray[1]);
                break;
            case "css":
            case "cssselector":
                by = By.cssSelector(locatorArray[1]);
                break;
            case "classname":
                by = By.className(locatorArray[1]);
                break;
            case "tagname":
                by = By.tagName(locatorArray[1]);
                break;
            case "linktext":
                by = By.linkText(locatorArray[1]);
                break;
            case "partiallinkText":
                by = By.partialLinkText(locatorArray[1]);
                break;
            case "accessibilityid":
                by= MobileBy.AccessibilityId(locatorArray[1]);
                break;
            case "androiduiautomator":
                by = MobileBy.AndroidUIAutomator(locatorArray[1]);
                break;
            case "Iosuiautomation":
                by=MobileBy.IosUIAutomation(locatorArray[1]);
            default:
                by = null;
                //break;

        }

        return by;
    }

    public WebElement getWeElement() {
        try {
            if(webDriver instanceof AndroidDriver){
                element=((AndroidDriver)webDriver).findElement(getLocator());
            }else  if(webDriver instanceof IOSDriver){
                element=((IOSDriver)webDriver).findElement(getLocator());
            }else {

                element = webDriver.findElement(getLocator());
            }
        } catch (NoSuchElementException nse) {
            nse.printStackTrace();
            LOGGER.error("Unable to locate element:"+getLocator().toString());
            element = null;
        }


        return element;
    }

    public List<WebElement> getWeElementList() {
        try {
            if(webDriver instanceof AndroidDriver){
                elementList=((AndroidDriver)webDriver).findElements(getLocator());
            }else  if(webDriver instanceof IOSDriver){
                elementList=((IOSDriver)webDriver).findElements(getLocator());
            }else {

                elementList = webDriver.findElements(getLocator());
            }
        } catch (NoSuchElementException nse) {
            nse.printStackTrace();
            LOGGER.error("Unable to locate element:"+getLocator().toString());
            element = null;
        }
        return elementList;
    }

    public AppiumDriver<?> getMobileDriver() {
        if (webDriver instanceof AndroidDriver) {
            appiumDriver = (AndroidDriver<?>) webDriver;

        } else if (webDriver instanceof IOSDriver) {
            appiumDriver = (IOSDriver<?>) webDriver;
        }
        return appiumDriver;
    }

}
