package com.hc.test.framework.keywords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hc.test.framework.core.CustomFunctions;
import com.hc.test.framework.dao.UserAccessCodeTestDao;
import com.hc.test.framework.entities.UserAccessCode;
import com.hc.test.framework.utils.PropertiesUtil;


public class ServiceKeywords<U,V> extends CustomFunctions {
	
//	private HashMap<String, HashMap<String, String>> params=new HashMap<String, HashMap<String,String>>();
	private HashMap<String, String> params=new HashMap<String, String>();
	private String url;
	
	@Autowired
	private PropertiesUtil properties;
	
	@Autowired
	private UserAccessCodeTestDao userAccessCodeTestDao;
	
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");

    }
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	public boolean setParams(Configuration objRepo, WebDriver driver,String objKey, String data) throws Exception{
		
		try{
			String [] param=data.split("=");
			logger.info("Setting the parameters for "+objKey);
			logger.info("Parameter name:"+param[0]);
			logger.info("parameter value:"+param[1]);
			if(param[0].contains("url")){
				url=param[0];
			}else{
				params.put(param[0], param[1]);	
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}	
	}
	
//	@SuppressWarnings("unchecked")
//	public ResponseEntity<V> post(U payLoad,String url) throws Exception{
//		ResponseEntity<V> postResponse = null;
//		HttpHeaders requestHeaders;
//		try{
//    		   
//		    HttpEntity<U> entity = new HttpEntity<U>(payLoad, requestHeaders);
//		      
//			RestTemplate restTemplate=new RestTemplate();
//			restTemplate.setMessageConverters(getMessageConverters());
//			postResponse= (ResponseEntity<V>) restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
//			return postResponse;
//
//		}catch(RestClientException rce){
//			logger.error("RestClientException occured:"+rce.getMessage());
//			throw new Exception(rce.getMessage());
//		}catch (Exception e){
//			logger.error("Exception occured:"+e.getMessage());
//			throw new Exception(e.getMessage());
//		}
//	}
		
		@SuppressWarnings("unchecked")
		public boolean get(Configuration objRepo, WebDriver driver,
				String objKey, String data) throws Exception{
			ResponseEntity<V> getResponse = null;
			HttpHeaders requestHeaders;
			try{
				requestHeaders=setHeaders("helpchat");
				HttpEntity<String> entity = new HttpEntity<String>("parameters", requestHeaders);		      
				RestTemplate restTemplate=new RestTemplate();
				restTemplate.setMessageConverters(getMessageConverters());
				getResponse= (ResponseEntity<V>) restTemplate.exchange(url, HttpMethod.GET, entity, Object.class,params);
				if(getResponse.getStatusCode().is2xxSuccessful()){
					logger.info("Response :"+getResponse.getStatusCode().value());
					return true;
				}else{
					return false;	
				}
			}catch(RestClientException rce){
				logger.error("RestClientException occured:"+rce.getMessage());
				return false;	
			}catch (Exception e){
				logger.error("Exception occured:"+e.getMessage());
				return false;	
			}
				
		}	
	

	
	private static List<HttpMessageConverter<?>> getMessageConverters() {
	    List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	    converters.add(new MappingJackson2HttpMessageConverter());
	    return converters;
	}
	
	private HttpHeaders setHeaders(String appType) throws Exception{
		try{
		    HttpHeaders requestHeaders=new HttpHeaders();
		    requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		    
		    if(appType.equals("helpchat")){
			    UserAccessCode userAccessCode=userAccessCodeTestDao.findLatestAccessCodeByUserId(Integer.parseInt(properties.getProperty("userId")));
			    
			    if(userAccessCode==null){
			    	logger.debug("Unable to find a valid access token for the user:"+properties.getProperty("userId"));
			    	throw new Exception("No valid auth token found for user id:"+properties.getProperty("userId"));
			    }		    
			    requestHeaders.add("X-AKOSHA-AUTH",userAccessCode.getAkoshaAccessCode());
			    logger.info("X_Akosha_Auth:"+userAccessCode.getAkoshaAccessCode());
		    }
		    
		    return requestHeaders;
		}catch(Exception e){
			throw new Exception (e.getMessage());
		}
	}
}
