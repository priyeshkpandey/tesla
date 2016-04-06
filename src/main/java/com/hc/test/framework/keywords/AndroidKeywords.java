package com.hc.test.framework.keywords;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration2.Configuration;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hc.test.framework.core.CustomFunctions;
import com.hc.test.framework.utils.DriverUtils;
import com.hc.test.framework.utils.PropertiesUtil;
import com.hc.test.framework.utils.ReadConfiguration;

public class AndroidKeywords extends CustomFunctions {

	private Configuration prop;
	private ReadConfiguration readConfig;

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

}
