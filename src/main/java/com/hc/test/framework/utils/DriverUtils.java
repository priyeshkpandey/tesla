package com.hc.test.framework.utils;

import com.google.common.base.Function;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DriverUtils {
    public WebDriver webDriver;
    public Configuration objRepo;
    public String objKey;
    public By by;
    private boolean shouldCache=false;
    String locator;
    WebElement element;
    List<WebElement> elementList;
    AndroidDriver androidDriver;
    IOSDriver iosDriver;
    private AppiumDriver appiumDriver;
    private Pattern p;
    private Matcher matcher;

    public DriverUtils(WebDriver webDriver, Configuration objRepo, String objKey) {
        this.webDriver = webDriver;
        this.objKey = objKey;
        this.objRepo = objRepo;
        locator = objRepo.getString(objKey);
        if(locator.length()>0)
          by=getLocator();
        else
            LOGGER.info("No locator found");

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
                break;
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

    public MobileDriver getMobileDriver() {

        return (MobileDriver)webDriver;
    }

    public  WebElement findElement(final WebDriver webDriver,final By by){
        WebElement webElement1;

        if(webDriver instanceof AndroidDriver || webDriver instanceof IOSDriver) {

            webElement1 = new FluentWait<>((AppiumDriver)webDriver)
                    .withTimeout(Constants.DEFAULT_WAIT_TIMEOUT, TimeUnit.SECONDS)
                    .pollingEvery(Constants.DEFAULT_POLLING_TIMEOUT, TimeUnit.MILLISECONDS)
                    .ignoring(ElementNotVisibleException.class)
                    .withMessage("Element could not be found. Finder - " + by.toString())
                    .until(new Function<AppiumDriver, WebElement>() {
                        @Override
                        public WebElement apply(AppiumDriver driver) {
                            LOGGER.info("Searching for element - " + by.toString());
                            driver = (AppiumDriver)webDriver;
                            return driver.findElement(by);
                        }
                    });



        }else{
            webElement1=new FluentWait<>(webDriver)
                    .withTimeout(Constants.DEFAULT_WAIT_TIMEOUT, TimeUnit.SECONDS)
                    .pollingEvery(Constants.DEFAULT_POLLING_TIMEOUT, TimeUnit.MILLISECONDS)
                    .ignoring(ElementNotVisibleException.class)
                    .withMessage(
                            "Element could not be found. Finder - "
                                    + by.toString())
                    .until(new Function<WebDriver, WebElement>()
                    {
                        @Override
                        public WebElement apply(WebDriver driver)
                        {
                            LOGGER.info("Searching for element - "
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
                        LOGGER.info("Checking if element is displayed - "
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

    public void getCurrentActivity(){

    }

    public String getTextByPattern(String text,String data){
        String pattern1="/([1-9][0-9]*)|0";
        String searchedText=null;
        String key = data.split(",")[0];
        String pattern = data.split(",")[1];
        p=Pattern.compile(pattern1);
        matcher=p.matcher(text);
        if(matcher.find()){
            searchedText=text.substring(matcher.start(),matcher.end());
        }else{
            LOGGER.error("No match found for "+pattern+"in the text "+text);
        }

        return searchedText;
    }

    public void endTest(){
        if(webDriver instanceof AndroidDriver || webDriver instanceof IOSDriver){
            ((AppiumDriver)webDriver).quit();
        }else{
            webDriver.close();
            webDriver.quit();
        }
    }

    public void switchToContext(String context){

        Map<String,String> params = new HashMap<String,String>();
        params.put("name", context);

        if(webDriver instanceof AndroidDriver){
            ((AndroidDriver)webDriver).execute(DriverCommand.SWITCH_TO_CONTEXT,params);
        }else{
            ((IOSDriver)webDriver).execute(DriverCommand.SWITCH_TO_CONTEXT,params);
        }
    }
    
    public void scrollUp(int duration){
    	AndroidDriver androidDriver=(AndroidDriver)webDriver;
		Dimension size = androidDriver.manage().window().getSize();
        System.out.println(size);
        int endY=(int) (size.width*0.10);
        int startY=(int) (size.width*0.9);
        int startX=size.height/2;
        androidDriver.swipe(startX, startY, startX, endY, duration);
		
		}




}
