package com.hc.test.framework.utils;

import com.google.common.base.Function;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
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
        by=getLocator();

    }

    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
    }

    public static Logger LOGGER = LoggerFactory.getLogger(DriverUtils.class);

    public By getLocator() {

        String locatorArray[] = locator.split("@=@");

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
                LOGGER.error(Arrays.toString(locatorArray)+" is not a valid locator");
                //break;

        }

        return by;
    }

    public WebElement getWebElement() {
        element=findElement(webDriver,by);

        return element;
    }

    public List<WebElement> getWebElementList() {

        elementList=findelements(webDriver,by);
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

    public  WebElement findElement(final WebDriver webDriver,final By by){
        WebElement webElement1;

        if(webDriver instanceof AndroidDriver || webDriver instanceof IOSDriver) {

            webElement1 = new FluentWait<>((AppiumDriver)webDriver)
                    .withTimeout(Constants.DEFAULT_WAIT_TIMEOUT, TimeUnit.SECONDS)
                    .pollingEvery(Constants.DEFAULT_POLLING_TIMEOUT, TimeUnit.MILLISECONDS)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(ElementNotVisibleException.class)
                    .withMessage("Element could not be found. Finder - " + by.toString())
                    .until(new Function<AppiumDriver, WebElement>() {
                        @Override
                        public WebElement apply(AppiumDriver driver) {
                            LOGGER.debug("Searching for element - " + by.toString());
                            driver = (AppiumDriver)webDriver;
                            return driver.findElement(by);
                        }
                    });



        }else{
            webElement1=new FluentWait<>(webDriver)
                    .withTimeout(Constants.DEFAULT_WAIT_TIMEOUT, TimeUnit.SECONDS)
                    .pollingEvery(Constants.DEFAULT_POLLING_TIMEOUT, TimeUnit.MILLISECONDS)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(ElementNotVisibleException.class)
                    .withMessage(
                            "Element could not be found. Finder - "
                                    + by.toString())
                    .until(new Function<WebDriver, WebElement>()
                    {
                        @Override
                        public WebElement apply(WebDriver driver)
                        {
                            LOGGER.debug("Searching for element - "
                                    + by.toString());
                            driver = webDriver;
                            return driver.findElement(by);
                        }
                    });
        }

        new FluentWait<>(webElement1)
                .withTimeout(6, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(WebDriverException.class)
                .withMessage(
                        "Element was not visible. Finder - "
                                + by.toString())
                .until(new Function<WebElement, Boolean>()
                {
                    @Override
                    public Boolean apply(WebElement webElement)
                    {
                        LOGGER.debug("Checking if element is displayed - "
                                + by.toString());
                        return webElement.isDisplayed();
                    }
                });

        return webElement1;
    }


    public List<WebElement> findelements(final WebDriver webDriver,final By by){
        List<WebElement> webElementList;
        if(webDriver instanceof AndroidDriver || webDriver instanceof IOSDriver){

            webElementList = new FluentWait<>((AppiumDriver)webDriver).withTimeout(Constants.DEFAULT_WAIT_TIMEOUT, TimeUnit.SECONDS)
                    .pollingEvery(Constants.DEFAULT_POLLING_TIMEOUT, TimeUnit.MILLISECONDS)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(ElementNotVisibleException.class)
                    .withMessage("Element could not be found. Finder - " + by.toString())
                    .until(new Function<AppiumDriver, List<WebElement>>() {
                        @Override
                        public List<WebElement> apply(AppiumDriver driver) {
                            LOGGER.debug("Searching for element - " + by.toString());
                            driver = (AppiumDriver)webDriver;
                            return driver.findElements(by);
                        }
                    });
        }else{
            webElementList = new FluentWait<>(webDriver).withTimeout(Constants.DEFAULT_WAIT_TIMEOUT, TimeUnit.SECONDS)
                    .pollingEvery(Constants.DEFAULT_POLLING_TIMEOUT, TimeUnit.MILLISECONDS)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(ElementNotVisibleException.class)
                    .withMessage("Element could not be found. Finder - " + by.toString())
                    .until(new Function<WebDriver, List<WebElement>>() {
                        @Override
                        public List<WebElement> apply(WebDriver driver) {
                            LOGGER.debug("Searching for element - " + by.toString());
                            driver = webDriver;
                            return driver.findElements(by);
                        }
                    });

        }


        return webElementList;
    }
    public AndroidDriver getAndroidDriver(){
            return (AndroidDriver) webDriver;

    }

    public IOSDriver getIosDriver(){
        return (IOSDriver) webDriver;

    }

    public boolean startActivity(String activityName){


        try{
            getAndroidDriver().startActivity(Constants.PACKAGE_NAME,activityName);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
