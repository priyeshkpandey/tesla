package com.hc.test.framework.keywords;

import java.io.IOException;
import java.text.MessageFormat;

import com.hc.test.framework.utils.Constants;
import io.appium.java_client.TouchAction;
import org.apache.commons.configuration2.Configuration;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hc.test.framework.core.CustomFunctions;
import com.hc.test.framework.utils.PropertiesUtil;
import com.hc.test.framework.utils.DriverUtils;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebElement;

@Component
public class AndroidKeywords extends CustomFunctions {

	@Autowired
	private PropertiesUtil propUtil;
	DriverUtils driverUtils;
	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(AndroidKeywords.class);

	public boolean callToCallbackApi(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean result = true;

		try {
			String platform = data.split(",")[0];
			String amount = data.split(",")[1];
			String orderId = data.split(",")[2];
			String paymentMode = data.split(",")[3];
			Object[] checkoutCallbackParams = { platform };
			Object[] checkoutCallbackAPIParams = { amount, orderId, paymentMode };
			String checkoutBaseUrl = propUtil.getProperty("api.checkout.base");
			MessageFormat checkoutCallbackFormat = new MessageFormat(
					propUtil.getProperty("checkout.callback.success"));
			MessageFormat paramsFormat = new MessageFormat(
					propUtil.getProperty("checkout.callback.params"));
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
				LOGGER.error("Response code for checkout callback is "+respCode);
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
            TouchActions actions= new TouchActions(driverUtils.getMobileDriver());
            actions.singleTap(element).perform();
        }
        catch (Exception e){
            istappedin=false;
        }
        return istappedin;
    }

}
