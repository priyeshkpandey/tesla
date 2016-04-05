package com.hc.test.framework.utils;

import io.appium.java_client.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by sabyasachi on 05/04/16.
 */
public class MobileGestures {
    private TouchAction touchAction;
    MobileDriver mobileDriver;
    WebDriver webDriver;

    public MobileGestures(WebDriver webDriver){

        this.touchAction = new TouchAction(((MobileDriver)webDriver));
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

    public static void swipeUp(WebElement onElement,int duration){
        ((MobileElement)onElement).swipe(SwipeElementDirection.UP, duration);
    }

    public static void swipeDown(WebElement onElement,int duration){
        ((MobileElement)onElement).swipe(SwipeElementDirection.DOWN, duration);
    }

    public static void swipeRight(WebElement onElement,int duration){
        ((MobileElement)onElement).swipe(SwipeElementDirection.RIGHT, duration);
    }

    public static void swipeLeft(WebElement onElement,int duration){
        ((MobileElement)onElement).swipe(SwipeElementDirection.LEFT, duration);
    }

    public MobileGestures perform(){
        setTouchAction(touchAction.perform());
        return this;
    }
}
