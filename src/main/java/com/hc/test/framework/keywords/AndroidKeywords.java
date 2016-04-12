package com.hc.test.framework.keywords;

import io.appium.java_client.TouchAction;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import com.hc.test.framework.utils.DatabaseUtil;
import com.hc.test.framework.utils.DriverUtils;
import com.hc.test.framework.utils.MobileGestures;
import com.hc.test.framework.utils.PropertiesUtil;
import com.hc.test.framework.utils.ReadConfiguration;

public class AndroidKeywords extends CustomFunctions {

	private Configuration prop;
	private ReadConfiguration readConfig;

	private PropertiesUtil propUtil;
	DriverUtils driverUtils;
	private HashMap<String, HashMap<String, String>> queryResults;

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(AndroidKeywords.class);

	public boolean callToCheckoutCallbackApi(Configuration objRepo,
			WebDriver driver, String objKey, String data) {
		boolean result = true;

		readConfig = new ReadConfiguration("config");
		prop = readConfig.getConfigFile();

		try {
			String platform = data.split(",")[0];
			String mobileNo = data.split(",")[1];
			DatabaseUtil dbUtil = new DatabaseUtil("orders");
			dbUtil.createConnection();
			MessageFormat userIdQuery = new MessageFormat(
					prop.getString("userid.from.mobile"));
			Object[] userParams = { mobileNo };
			queryResults = dbUtil.getQueryResultAsMapForSingleTable(userIdQuery
					.format(userParams));
			String userId = queryResults.get("user").get("id1");
			MessageFormat latestOrderQuery = new MessageFormat(
					prop.getString("checkout.latest.order"));
			Object[] latestOrderParams = { userId };
			queryResults = dbUtil
					.getQueryResultAsMapForSingleTable(latestOrderQuery
							.format(latestOrderParams));
			dbUtil.closeConnection();
			String amount = queryResults.get("orders").get("final_payable_value1");
			String txId = queryResults.get("orders").get("transaction_id1");
			String paymentMode = data.split(",")[2];
			Object[] checkoutCallbackParams = { platform };
			Object[] checkoutCallbackAPIParams = { amount, txId, paymentMode };
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
			LOGGER.info("Callback url:  "+callbackUrl);
			Integer respCode = callbackResponse.getStatusLine().getStatusCode();

			if (!respCode.toString().startsWith("20")) {

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

	public boolean enterText(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean isLoggedin = true;
		try {
			Thread.sleep(Constants.THREAD_SLEEP);
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			WebElement element = driverUtils.getWebElement();
			// element.clear();
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
    
    public boolean tapOnCheckBox(Configuration objRepo, WebDriver driver, String objKey, String data) {
        boolean isClicked=false;

        try {

            driverUtils = new DriverUtils(driver,objRepo,objKey);
            WebElement element = driverUtils.getWebElement();
            if(data.equals("check")){
            	LOGGER.info("attribute value:"+element.getAttribute("checked"));
            	if(element.getAttribute("checked").equals("true")){
            		isClicked=true;
            	}else{
                    if(element.isEnabled()){
                    	element.click();
        	            if(element.getAttribute("checked").equals("true") ){
        	            	isClicked=true;
        	            }else{
        	            	LOGGER.info("Unable to select the checkbox:"+objKey);
        	            }
                    }else{
                    	LOGGER.info("Checkbox is not enabled to select:"+objKey);
                    }
            	}            		
            }else if(data.equals("uncheck")){
            	if(element.getAttribute("checked").equals("false") ){
            		isClicked=true;
            	}else{
                    if(element.isEnabled()){
                    	element.click();
        	            if(element.getAttribute("checked").equals("false") ){
        	            	isClicked=true;
        	            }else{
        	            	LOGGER.debug("Unable to deselect the checkbox:"+objKey);
        	            }
                    }else{
                    	LOGGER.debug("Checkbox is not enabled to deselect:"+objKey);
                    }
            	} 
            }

            	
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isClicked;
    }
    
	public boolean tapOnExpandView(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean isTapped = true;
		try {
			Thread.sleep(Constants.THREAD_SLEEP);
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			WebElement element = driverUtils.getWebElement();
			element.click();

		} catch (Exception e) {
			isTapped = false;
		}
		return isTapped;
	}

	public boolean clickAndEnterText(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean isTapped = true;
		try {
			Thread.sleep(Constants.THREAD_SLEEP);
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			WebElement element = driverUtils.getWebElement();
			element.click();
			element.sendKeys(data);

		} catch (Exception e) {
			isTapped = false;
		}
		return isTapped;
	}

	public boolean tapOnText(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean istappedin = true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			WebElement element = driverUtils.getWebElement();
			TouchAction actions = new TouchAction(driverUtils.getMobileDriver());
			actions.tap(element).perform();
		} catch (Exception e) {
			e.printStackTrace();
			istappedin = false;
		}
		return istappedin;
	}

	public boolean swipeUp(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean isSwipped = true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			MobileGestures.swipeUp(driverUtils.getWebElement(),
					Constants.SWIPE_DURATION);
		} catch (Exception e) {
			isSwipped = false;

		}
		return isSwipped;

	}

	public boolean swipeDown(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean isSwipped = true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			MobileGestures.swipeDown(driverUtils.getWebElement(),
					Constants.SWIPE_DURATION);
		} catch (Exception e) {
			isSwipped = false;

		}
		return isSwipped;

	}

	public boolean swipeLeft(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean isSwipped = true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			MobileGestures.swipeLeft(driverUtils.getWebElement(),
					Constants.SWIPE_DURATION);
		} catch (Exception e) {
			isSwipped = false;

		}
		return isSwipped;

	}

	public boolean swipeRight(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean isSwipped = true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			MobileGestures.swipeRight(driverUtils.getWebElement(),
					Constants.SWIPE_DURATION);
		} catch (Exception e) {
			isSwipped = false;

		}
		return isSwipped;

	}

	public boolean verifyExactText(Configuration objectRepo,WebDriver driver,String objKey,String data){
		boolean isEqual=false;
		String actualText=null;
		try {
			driverUtils = new DriverUtils(driver, objectRepo, objKey);
			 actualText=driverUtils.getWebElement().getText().trim();
			if(actualText.equals(data)){
				isEqual= true;
			}
		}catch (Exception e){
			LOGGER.error(actualText+" is not equal with "+data);
			LOGGER.error("\n"+ExceptionUtils.getStackTrace(e));
		}
		return isEqual;
	}

	public boolean verifyTextByPattern(Configuration objectRepo,WebDriver driver,String objKey,String data){
		boolean isMatch=false;
		String actualText;
		try {
			driverUtils=new DriverUtils(driver, objectRepo, objKey);
			actualText=driverUtils.getWebElement().getText();
			if(actualText.equals(driverUtils.getTextByPattern(actualText,data))){
				isMatch=true;
			}
		}catch (Exception e){
			LOGGER.error("\n"+ExceptionUtils.getStackTrace(e));
		}
		return isMatch;
	}



	public boolean endTest(Configuration objectRepo,WebDriver driver,String objKey,String data ){
		try {
			driverUtils = new DriverUtils(driver, objectRepo, objKey);
			driverUtils.endTest();
			return true;
		}catch (Exception e){
			LOGGER.error("\n"+ExceptionUtils.getStackTrace(e));
			return false;
		}
	}

}
