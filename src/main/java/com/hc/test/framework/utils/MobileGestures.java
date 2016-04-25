package com.hc.test.framework.utils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sabyasachi on 05/04/16.
 */
public class MobileGestures {
    private TouchAction touchAction;
    MobileDriver mobileDriver;
    WebDriver webDriver;


    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
    }

    public static Logger LOGGER = LoggerFactory.getLogger(MobileGestures.class);

    public MobileGestures(WebDriver webDriver){

        this.touchAction = new TouchAction(((MobileDriver)webDriver));
        this.webDriver=webDriver;
    }


    public void setTouchAction(TouchAction touchAction) {
        this.touchAction = touchAction;
    }

    public MobileGestures tap(WebElement element){
        setTouchAction(touchAction.tap(element));

        return this;
    }

    public MobileGestures longPress(WebElement onElement) {
        setTouchAction(touchAction.longPress(onElement));
        return this;
    }

    public MobileGestures perform(){
        setTouchAction(touchAction.perform());
        return this;
    }


    public static void swipeUp(WebElement onElement) {

        ((MobileElement) onElement).swipe(SwipeElementDirection.UP, Constants.SWIPE_DURATION);
        LOGGER.info("Swipped up on->" + onElement.toString());
    }

    public static void swipeDown(WebElement onElement) {

        ((MobileElement) onElement).swipe(SwipeElementDirection.DOWN, Constants.SWIPE_DURATION);
        LOGGER.info("Swipped  on->" + onElement.toString());
    }

    public static void swipeRight(WebElement onElement) {

        ((MobileElement) onElement).swipe(SwipeElementDirection.RIGHT, Constants.SWIPE_DURATION);
        LOGGER.info("Swipped up on->" + onElement.toString());
    }

    public static void swipeLeft(WebElement onElement) {

        ((MobileElement) onElement).swipe(SwipeElementDirection.LEFT, Constants.SWIPE_DURATION);
        LOGGER.info("Swipped up on->" + onElement.toString());
    }

}

