package com.hc.test.framework.keywords;

import com.hc.test.framework.core.CustomFunctions;
import com.hc.test.framework.utils.*;
import com.hc.test.framework.utils.*;
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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public boolean getTextAndStoreByPattern(Configuration objRepo, WebDriver driver,
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
            	if(element.getAttribute("checked").trim().equalsIgnoreCase("true")){
            		isClicked=true;
            	}else{
                    if(element.getAttribute("enabled").trim().equalsIgnoreCase("true")){
                    	element.click();
        	            if(element.getAttribute("checked").trim().equalsIgnoreCase("true") ){
        	            	isClicked=true;
        	            }else{
        	            	LOGGER.info("Unable to select the checkbox:"+objKey);
        	            }
                    }else{
                    	LOGGER.info("Checkbox is not enabled to select:"+objKey);
                    }
            	}            		
            }else if(data.equals("uncheck")){
            	if(element.getAttribute("checked").trim().equalsIgnoreCase("false") ){
            		isClicked=true;
            	}else{
                    if(element.getAttribute("enabled").trim().equalsIgnoreCase("true")){
                    	element.click();
        	            if(element.getAttribute("checked").trim().equalsIgnoreCase("false") ){
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


    public boolean tapOnText(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean istappedin=true;
        try {
			Thread.sleep(Constants.THREAD_SLEEP);
            driverUtils= new DriverUtils(driver, objRepo, objKey);
            LOGGER.info("Text"+ data +" is to be tapped");
            WebElement element=driverUtils.getWebElement();
            TouchAction actions= new TouchAction(driverUtils.getMobileDriver());
            actions.tap(element).perform();
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.info("Text"+ data +" could not tapped");
            istappedin=false;
        }
        return istappedin;
    }


    public boolean hideKeyPad(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean ishidden=true;
        try {
            driverUtils= new DriverUtils(driver, objRepo, objKey);
            driverUtils.getAndroidDriver().hideKeyboard();
        }
        catch (Exception e){
            e.printStackTrace();
            ishidden=true;
            LOGGER.info("Keyword is not executed");
            ishidden=true;
        }
        return ishidden;
    }


	public boolean swipeUp(Configuration objRepo, WebDriver driver,
			String objKey, String data) {
		boolean isSwipped = true;
		try {
			driverUtils = new DriverUtils(driver, objRepo, objKey);
			MobileGestures.swipeUp(driverUtils.getWebElement());
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
			MobileGestures.swipeDown(driverUtils.getWebElement());
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
			MobileGestures.swipeLeft(driverUtils.getWebElement());
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
			MobileGestures.swipeRight(driverUtils.getWebElement());
		} catch (Exception e) {
			isSwipped = false;

		}
		return isSwipped;

	}

	public boolean verifyExactText(Configuration objectRepo,WebDriver driver,String objKey,String data){
		boolean isEqual=false;
		String actualText=null;
		try {
			Thread.sleep(Constants.THREAD_SLEEP);
			driverUtils = new DriverUtils(driver, objectRepo, objKey);
			 actualText=driverUtils.getWebElement().getText().trim();
			if(actualText.equals(data.trim())){
				isEqual= true;
				System.out.println(actualText+"Equals to->"+data);
			}
		}catch (Exception e){
			System.out.println(actualText+" is not equal with "+data);
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


	public boolean pressDeviceBack(Configuration objectRepo,WebDriver driver,String objKey,String data){
		boolean isClicked=true;
		try{

			Thread.sleep(Constants.THREAD_SLEEP);
			driverUtils=new DriverUtils(driver, objectRepo, objKey);

			driverUtils.getAndroidDriver().pressKeyCode(4);

		}catch (Exception e){
			e.printStackTrace();
			isClicked=false;
		}

		return isClicked;

	}

	public boolean tapTillElementPresent(Configuration objectRepo,WebDriver driver,String objKey,String data){
		try {
			driverUtils = new DriverUtils(driver, objectRepo, objKey);
			WebElement element=driverUtils.getWebElement();
			for(int i=0;i<4;i++) {
				if (element.isDisplayed()) {
					element.click();

				}
			}


		}catch (Exception e){
			e.printStackTrace();
		}
		return true;
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

	public boolean longPressOnElement(Configuration objectRepo, WebDriver driver, String objKey, String data) {
		boolean isTapped = false;
		try {
			driverUtils = new DriverUtils(driver, objectRepo, objKey);
			List<WebElement> webElements = driverUtils.getWebElementList();
			Random random = new Random();
			new MobileGestures(driver).longPress(webElements.get(random.nextInt(webElements.size()))).perform();
			isTapped = true;
		} catch (Exception e) {
			ExceptionUtils.getStackTrace(e);
		}
		return isTapped;
	}


	public boolean isElementDisplayed(Configuration objectRepo, WebDriver driver, String objKey, String data){
		boolean isDisplayed=false;

		try{
			driverUtils=new DriverUtils(driver,objectRepo,objKey);
			List<WebElement> allElements=driverUtils.getWebElementList();
			if(allElements.size()==1){
				isDisplayed=allElements.get(0).isDisplayed();
			}else if(allElements.size()>1){

				Random random=new Random();
				isDisplayed=allElements.get(random.nextInt(allElements.size())).isDisplayed();
			}

		}catch (Exception e){
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			LOGGER.error("element is not displayed");
		}
		return isDisplayed;
	}

	public boolean verifyFromMap(Configuration objectRepo, WebDriver driver, String objKey, String data){

		boolean isVerified=false;
		String valueFromMap=masterMap.get(data).toString();
		try {
			driverUtils = new DriverUtils(driver, objectRepo, objKey);
			WebElement element = driverUtils.getWebElement();
			LOGGER.info("Master map Content=>"+masterMap);

			if(valueFromMap.equals(element.getText())){
				isVerified=true;
			}
		}catch (Exception e){
			LOGGER.error("Verirification failed from map");
			LOGGER.error(ExceptionUtils.getStackTrace(e));

		}
		return isVerified;
	}

    
    public boolean waitForExistence(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean isexisting=true;
        try {
            driverUtils= new DriverUtils(driver, objRepo, objKey);
            WebElement element=driverUtils.getWebElement();
            WaitUtil.waitForElementPresent(element);
            LOGGER.info("Checking if "+element.toString()+" is present in keywords");
        }
        catch (Exception e){
            e.printStackTrace();
            isexisting=false;
            LOGGER.info("Element Not Found");
        }
        return isexisting;
    }
    public boolean selectByValue(Configuration objRepo, WebDriver driver, String objKey, String data){
        boolean isselected=true;
        try {
            driverUtils = new DriverUtils(driver, objRepo, objKey);
            List<WebElement> element = driverUtils.getWebElementList();
            System.out.println(element.toString());
                for (WebElement option : element) {
                    if ((option.getText().trim()).equals(data.trim())) {
                        LOGGER.info("Checking if " + data + " is selected in keywords");
                        option.click();
                        break;
                    }
                }
        }
        catch (Exception e){
            e.printStackTrace();
            isselected=false;
            LOGGER.info("selectByValue keyword execution failed");
        }
        return isselected;
    }
}
