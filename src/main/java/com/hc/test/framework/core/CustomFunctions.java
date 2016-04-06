package com.hc.test.framework.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.hc.test.framework.chat.dao.UserAccessCodeTestDao;
import com.hc.test.framework.chat.entities.UserAccessCode;

import com.hc.test.framework.utils.PropertiesUtil;

@Component
public class CustomFunctions<V> {
	
//	private HashMap<String, HashMap<String, String>> params=new HashMap<String, HashMap<String,String>>();
	private HashMap<String, String> params;
	@Autowired
	private UserAccessCodeTestDao userAccessCodeTestDao;
	
	@Autowired
	private PropertiesUtil properties;

    HttpHeaders requestHeaders;
    
    public static HashMap<String, Object> masterMap = new HashMap<String, Object>();

//	@Autowired
//	private CustomFunctions custonFunctions;
	
//	Test test=new Test();
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");

    }
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public boolean setParams(String data) throws Exception{
    	params=new HashMap<String, String>();
    		try{
    			
    			String [] param=data.split("=");
//    			if(param[0].contains("url")){
//    				url=param[1];
//    			}else{
    				params.put(param[0], param[1]);	
//    			}
    			return true;
    		}catch(Exception e){
    			e.printStackTrace();
    			logger.error(e.getMessage());
    			return false;
    		}
    	
    }
    
    public boolean 	setHeaders(String appType) throws Exception{
    
    	boolean isHeadersSet=false;
    	requestHeaders=new HttpHeaders();
    	System.out.println(params.get("zipcode"));
		try{
			
		    requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		    if(appType.equals("helpchat")){
		    	logger.info("find a valid access token for the user:"+properties.getProperty("userId"));
			    UserAccessCode userAccessCode=userAccessCodeTestDao.findLatestAccessCodeByUserId(Integer.parseInt(properties.getProperty("userId")));
//			    UserAccessCode userAccessCode=userAccessCodeTestDao.findLatestAccessCodeByUserId(305257);
			    if(userAccessCode==null){
			    	logger.info("Unable to find a valid access token for the user:"+properties.getProperty("userId"));
			    	throw new Exception("No valid auth token found for user id:"+properties.getProperty("userId"));
			    }else{
			    	isHeadersSet=true;
				    requestHeaders.add("X-AKOSHA-AUTH",userAccessCode.getAkoshaAccessCode());
				    logger.info("X_Akosha_Auth:"+userAccessCode.getAkoshaAccessCode());
			    }
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception (e.getMessage());
		}
		return isHeadersSet;
    }

	@SuppressWarnings("unchecked")
	public boolean get(String url) throws Exception{
		ResponseEntity<V> getResponse = null;
		
		try{
			String [] param=url.split("=");
			HttpEntity<String> entity = new HttpEntity<String>("parameters", requestHeaders);		      
			RestTemplate restTemplate=new RestTemplate();
			restTemplate.setMessageConverters(getMessageConverters());
			getResponse= (ResponseEntity<V>) restTemplate.exchange(param[1], HttpMethod.GET, entity, Object.class,params);
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
			StackTraceElement[] ste=e.getStackTrace();
			logger.error("Null pointer exception while accesing "+ste[0].getClassName()+"-"+ste[0].getMethodName()+"-"+ste[0].getLineNumber());
			return false;	
		}
			
	}	
    
	private static List<HttpMessageConverter<?>> getMessageConverters() {
	    List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	    converters.add(new MappingJackson2HttpMessageConverter());
	    return converters;
	}
}
