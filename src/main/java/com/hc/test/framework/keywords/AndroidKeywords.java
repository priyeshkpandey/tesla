package com.hc.test.framework.keywords;


import io.appium.java_client.TouchAction;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration2.Configuration;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hc.test.framework.core.CustomFunctions;
import com.hc.test.framework.utils.Constants;
import com.hc.test.framework.utils.DriverUtils;
import com.hc.test.framework.utils.MobileGestures;
import com.hc.test.framework.utils.PropertiesUtil;
import com.hc.test.framework.utils.ReadConfiguration;



public class AndroidKeywords extends CustomFunctions {


	private Configuration prop;
	private ReadConfiguration readConfig;

	private PropertiesUtil propUtil;
	DriverUtils driverUtils;


	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(AndroidKeywords.class);

	public boolean callToCheckoutCallbackApi(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean result = true;

		readConfig = new ReadConfiguration("config");
		prop = readConfig.getConfigFile();

		try {
			String platform = data.split(",")[0];
			String amount = (String) masterMap.get(data.split(",")[1]);
			String orderId = (String) masterMap.get(data.split(",")[2]);
			String paymentMode = data.split(",")[3];
			Object[] checkoutCallbackParams = { platform };
			Object[] checkoutCallbackAPIParams = { amount, orderId, paymentMode };
			String checkoutBaseUrl = prop.getString("api.checkout.base");
			MessageFormat checkoutCallbackFormat = new MessageFormat(
					prop.getString("checkout.callback.success"));
			MessageFormat paramsFormat = new MessageFormat(
					prop.getString("checkout.callback.params"));
			CloseableHttpClient httpClient = HttpClients.createDefault();
			String callbackUrl = checkoutBaseUrl
					+ checkoutCallbackFormat.format(checkoutCallbackParams)
					+ paramsFormat.format(checkoutCallbackAPIParams);
			HttpPost checkoutCallbackPost = new HttpPost(callbackUrl);
			CloseableHttpResponse callbackResponse = httpClient
					.execute(checkoutCallbackPost);
			Integer respCode = callbackResponse.getStatusLine().getStatusCode();


			if(!respCode.toString().startsWith("20"))
			{

				result = false;
				LOGGER.error("Response code for checkout callback is "
						+ respCode);
			}

		} catch (ClientProtocolException e) {
			result = false;
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			result = false;
			LOGGER.error(e.getMessage());
		} finally {

		}

		return result;
	}


	boolean getTextAndStoreByPattern(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean result = true;

		WebElement element = new DriverUtils(driver, objRepo, objKey)
				.getWebElement();
		String entireText = element.getText();
		String key = data.split(",")[0];
		String pattern = data.split(",")[1];
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(entireText);

		if (m.find()) {
			String testToStore = entireText.substring(m.start(), m.end());
			masterMap.put(key, testToStore);
		} else {
			result = false;
			LOGGER.error("No match found for the pattern " + pattern
					+ " in the text: " + entireText);
		}

		return result;
	}





    public boolean enterText(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean isLoggedin=true;
        try {
            Thread.sleep(Constants.THREAD_SLEEP);
            driverUtils=new DriverUtils(driver, objRepo, objKey);
            WebElement element=driverUtils.getWebElement();
            //element.clear();
            element.sendKeys(data);

        }catch (Exception e){
            isLoggedin=false;
        }
        return isLoggedin;
    }

    public boolean tapOnButton(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean isLoggedin=true;
        try {
            Thread.sleep(Constants.THREAD_SLEEP);
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
            Thread.sleep(Constants.THREAD_SLEEP);
            driverUtils=new DriverUtils(driver, objRepo, objKey);
            driverUtils.getWebElement().clear();


        }catch (Exception e){
            isCleared=false;
        }
        return isCleared;
    }
    
    public boolean tapOnTile(Configuration objRepo, WebDriver driver, String objKey, String data) {
        boolean isClicked=false;

        try {

            driverUtils = new DriverUtils(driver,objRepo,objKey);
            driverUtils.getWebElement().click();
            isClicked=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isClicked;
    }
    
    public boolean tapOnRadioButton(Configuration objRepo, WebDriver driver, String objKey, String data) {
        boolean isClicked=false;

        try {

            driverUtils = new DriverUtils(driver,objRepo,objKey);
            WebElement parentElement = driverUtils.getWebElement();
            List<WebElement> childElements = parentElement.findElements(By.className("android.widget.RadioButton"));
            for(int i=0;i<childElements.size();i++){
            	if(childElements.get(i).getText().toLowerCase()
            			.equals(data.toLowerCase())){
            		childElements.get(i).click();         		
            		isClicked=true;
            	}
            }         
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isClicked;
    }
    
    
	public boolean tapOnExpandView(Configuration objRepo, WebDriver driver, String objKey, String data){
		boolean isTapped=true;
		try {
			Thread.sleep(Constants.THREAD_SLEEP);
			driverUtils= new DriverUtils(driver, objRepo, objKey);
			WebElement element=driverUtils.getWebElement();
			element.click();

		}catch (Exception e){
			isTapped=false;
		}
		return isTapped;
	}


	public boolean clickAndEnterText(Configuration objRepo, WebDriver driver, String objKey, String data){
		boolean isTapped=true;
		try {
			Thread.sleep(Constants.THREAD_SLEEP);
			driverUtils= new DriverUtils(driver, objRepo, objKey);
			WebElement element=driverUtils.getWebElement();
			element.click();
			element.sendKeys(data);

		}catch (Exception e){
			isTapped=false;
		}
		return isTapped;
	}


    public boolean tapOnText(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean istappedin=true;
        try {
            driverUtils= new DriverUtils(driver, objRepo, objKey);
            WebElement element=driverUtils.getWebElement();
            TouchAction actions= new TouchAction(driverUtils.getMobileDriver());
            actions.tap(element).perform();
        }
        catch (Exception e){
            e.printStackTrace();
            istappedin=false;
        }
        return istappedin;
    }

	public boolean swipeUp(Configuration objRepo, WebDriver driver, String objKey, String data){
		boolean isSwipped=true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			new MobileGestures(driver).swipeUp(driverUtils.getWebElement(),Constants.SWIPE_DURATION);
		}catch (Exception e){
			isSwipped=false;

		}
		return isSwipped;

	}
	public boolean swipeDown(Configuration objRepo, WebDriver driver, String objKey, String data){
		boolean isSwipped=true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			new MobileGestures(driver).swipeDown(driverUtils.getWebElement(),Constants.SWIPE_DURATION);
		}catch (Exception e){
			isSwipped=false;

		}
		return isSwipped;

	}

	public boolean swipeLeft(Configuration objRepo, WebDriver driver, String objKey, String data){
		boolean isSwipped=true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			new MobileGestures(driver).swipeLeft(driverUtils.getWebElement(),Constants.SWIPE_DURATION);
		}catch (Exception e){
			isSwipped=false;

		}
		return isSwipped;

	}

	public boolean swipeRight(Configuration objRepo, WebDriver driver, String objKey, String data){
		boolean isSwipped=true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			new MobileGestures(driver).swipeRight(driverUtils.getWebElement(),Constants.SWIPE_DURATION);
		}catch (Exception e){
			isSwipped=false;

		}
		return isSwipped;

	}

}
