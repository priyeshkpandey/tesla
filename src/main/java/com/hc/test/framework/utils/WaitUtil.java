package com.hc.test.framework.utils;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by sabyasachi on 31/03/16.
 */
public class WaitUtil {

    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
    }

    public static Logger LOGGER = LoggerFactory.getLogger(WaitUtil.class);

    public static boolean waitForElement(WebDriver driver, final WebElement element) {
        boolean isPresent = false;
        try {
            new WebDriverWait(driver, Constants.DEFAULT_WAIT_TIMEOUT) {
            }.until(new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(WebDriver driverObject) {
                    return element.isDisplayed();
                }
            });
            isPresent = element.isDisplayed();
            return isPresent;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;


    }

    public static boolean waitForElementPresent(final WebElement element) {
        boolean isDisplayed;
        try {

            isDisplayed = new FluentWait<WebElement>(element).withTimeout(Constants.DEFAULT_WAIT_TIMEOUT, TimeUnit.SECONDS)
                    .pollingEvery(Constants.DEFAULT_POLLING_TIMEOUT, TimeUnit.MILLISECONDS).ignoring(WebDriverException.class)
                    .ignoring(NoSuchElementException.class)
                    .withMessage("Element was not visible. Finder - " + element.toString())
                    .until(new Function<WebElement, Boolean>() {
                        public Boolean apply(WebElement webElement) {
                            LOGGER.debug("Checking if element is displayed - " + element.toString());
                            return webElement.isDisplayed();
                        }
                    });
        } catch (Exception e) {
            isDisplayed = false;
        }

        return isDisplayed;

    }



}
