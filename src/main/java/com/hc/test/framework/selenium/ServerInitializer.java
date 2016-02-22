package com.hc.test.framework.selenium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Component
public class ServerInitializer {

    RemoteWebDriver remoteWebDriver;

    DesiredCapabilities desiredCapabilities;
    SessionId driverSession;
    
    @Autowired
    @Qualifier("properties")
    Properties config;
    String executionPlatform;
    String targetOs;
    String serverurl;
    String buildpath;

    public String getExecutionPlatform() {
        return executionPlatform;
    }

    public void setExecutionPlatform(String executionPlatform) {
        this.executionPlatform = executionPlatform;
    }

    public String getTargetOs() {
        return targetOs;
    }

    public void setTargetOs(String targetOs) {
        this.targetOs = targetOs;
    }

    public String getServerurl() {
        return serverurl;
    }

    public void setServerurl(String serverurl) {
        this.serverurl = serverurl;
    }

    public String getBuildpath() {
        return buildpath;
    }

    public void setBuildpath(String buildpath) {
        this.buildpath = buildpath;
    }

    public ServerInitializer() {
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
        desiredCapabilities=getCapabability(executionPlatform);
        switch (executionPlatform.toUpperCase()) {
            case "WEBDRIVER":
            	LOGGER.info(getCapabability(executionPlatform).toString());
                //Need to call with port number <ipaddress>:4444/wd/hub
                remoteWebDriver = new RemoteWebDriver(new URL(serverurl + config.getProperty("webdriverWebUrl")), desiredCapabilities);
                remoteWebDriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
                remoteWebDriver.manage().window().maximize();
                break;

            case "ANDROID":
                if (null == serverurl) {
                    LOGGER.error("Server URL is NULL for ANDROID");
                } else {
                    LOGGER.info(desiredCapabilities.toString());
                    remoteWebDriver = new AndroidDriver(new URL(serverurl + config.getProperty("webdriverMobUrl")), desiredCapabilities);
                    remoteWebDriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
                }
                break;

            case "IOS":
                if (null == serverurl) {
                	LOGGER.error("Server URL is NULL for IOS");
                } else {
                    LOGGER.info(desiredCapabilities.toString());
                    remoteWebDriver = new IOSDriver(new URL(serverurl + config.getProperty("webdriverMobUrl")), desiredCapabilities);

                    remoteWebDriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
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
                desiredCapabilities.setCapability(MobileCapabilityType.APP,getBuildpath());
                break;

            case "IOS":
                desiredCapabilities=new DesiredCapabilities();
                String deviceId = getDeviceUdid();
                desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.4");
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.MAC);
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
                if (null == deviceId) {
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
                    desiredCapabilities.setCapability("locationServicesEnabled", true);
               } else {
                    LOGGER.info("Device ID:"+deviceId);
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
                desiredCapabilities.setCapability(MobileCapabilityType.APP, getBuildpath());
                break;

            default:
            	break;
            //TODO  Unknown for now

        }

        return desiredCapabilities;
    }

    public String getDeviceUdid() {
       String udid=null;
       String localurl= serverurl+config.getProperty("udidurlpython");
        //RequestGenerator req=new RequestGenerator(localurl);
        try{
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpGet getRequest = new HttpGet(localurl);
            HttpResponse response = httpClient.execute(getRequest);
            if(response.getStatusLine().getStatusCode()==200) {
                return udid;
            }
            else{
                return null;
            }
        }catch (Exception e){
            return null;
    }


    }
}
