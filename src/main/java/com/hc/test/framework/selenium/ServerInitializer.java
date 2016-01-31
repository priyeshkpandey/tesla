package com.hc.test.framework.selenium;

import com.hc.test.framework.utils.RequestGenerator;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.OS;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ServerInitializer {

    WebDriver remoteWebDriver;
    String serverurl;
    DesiredCapabilities desiredCapabilities;
    
    
    @Autowired
    @Qualifier("properties")
    Properties properties;
    String executionPlatform;
    String targetOs;

    public ServerInitializer() {
    }

    public ServerInitializer(String serverurl, String executionPlatform,String targetOs) {
        this.serverurl = serverurl;
        this.executionPlatform = executionPlatform;
        this.targetOs=targetOs;
        desiredCapabilities=getCapabability(executionPlatform);
    }
    
    static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(ServerInitializer.class);

    /*
    //Need to use android and ios driver as below
    
    if ( getDriver() instanceof IOSDriver ) {
         ((IOSDriver) getDriver()).click();
        }else if(getDriver() instance od AndroidDriver){
           ((AndroidDriver)getDriver()).click();
    }
     */
    public WebDriver getDriver() throws MalformedURLException {
        switch (executionPlatform.toUpperCase()) {
            case "WEBDRIVER":
            	LOGGER.info(getCapabability(executionPlatform).toString());
                //Need to call with port number <ipaddress>:4444/wd/hub
                remoteWebDriver = new RemoteWebDriver(new URL(serverurl + ":4444/wd/hub"), desiredCapabilities);
                break;

            case "ANDROID":
                if (null == serverurl) {
                    LOGGER.error("Server URL is NULL for ANDROID");
                } else {
                    remoteWebDriver = new AndroidDriver(new URL(serverurl + properties.getProperty("webdriverMobUrl")), desiredCapabilities);
                }
                break;

            case "IOS":
                if (null == serverurl) {
                	LOGGER.error("Server URL is NULL for IOS");
                } else {
                    remoteWebDriver = new IOSDriver(new URL(serverurl + properties.getProperty("webdriverMobUrl")), desiredCapabilities);
                }
                break;
                
                

        }

        return remoteWebDriver;
    }

    public DesiredCapabilities getCapabability(String executionPlatform) {
    	//desiredCapabilities=new DesiredCapabilities();

        switch (executionPlatform.toUpperCase()) {
        	

            case "WEBDRIVER":
                if (targetOs.toLowerCase().contains("win")) {
                	desiredCapabilities = DesiredCapabilities.chrome();
                	desiredCapabilities.setPlatform(Platform.WINDOWS);
                    
                } else if (targetOs.toLowerCase().contains("mac")) {
                	desiredCapabilities = DesiredCapabilities.chrome();
                	desiredCapabilities.setPlatform(Platform.MAC);
                } else {
                	desiredCapabilities = DesiredCapabilities.chrome();
                	desiredCapabilities.setPlatform(Platform.LINUX);
                }
                break;

            case "ANDROID":
                desiredCapabilities=new DesiredCapabilities();
                desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "android");
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANY);
                desiredCapabilities.setCapability(MobileCapabilityType.SUPPORTS_JAVASCRIPT, true);
                desiredCapabilities.setCapability(MobileCapabilityType.HAS_TOUCHSCREEN, true);
                desiredCapabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
                desiredCapabilities.setCapability(MobileCapabilityType.APP, "classpath:"+properties.getProperty("androidBuild"));
                break;

            case "IOS":
                desiredCapabilities=new DesiredCapabilities();
                String deviceId = getDeviceUdid();
                desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.4");
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.MAC);
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
                if (null == deviceId) {
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
                    desiredCapabilities.setCapability("locationServicesEnabled", true);
                } else {
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
                    desiredCapabilities.setCapability("udid", deviceId);
                }
                desiredCapabilities.setCapability(MobileCapabilityType.SUPPORTS_JAVASCRIPT, true);
                desiredCapabilities.setCapability(MobileCapabilityType.HAS_TOUCHSCREEN, true);
                desiredCapabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
                desiredCapabilities.setCapability("autoLaunch", true);
                desiredCapabilities.setCapability("showIOSLog", true);
                desiredCapabilities.setCapability("--force-ipad", false);
                desiredCapabilities.setCapability(MobileCapabilityType.APP, "classpath:"+properties.getProperty("iosBuild"));
                break;

            default:
            	break;
            //TODO  Unknown for now

        }

        return desiredCapabilities;
    }

    public String getDeviceUdid() {
       String udid=null;
       String localurl= serverurl+properties.getProperty("udidurlpython");
        RequestGenerator req=new RequestGenerator(localurl);
        if(req.getResponseObject().getStatusCode().value()==200){
            try {
                udid=req.parseJson(req.getResponseObject().getBody(),"udid");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            LOGGER.debug("No ios device found...Trying with simulator");
        }
		return udid;
    }

}
