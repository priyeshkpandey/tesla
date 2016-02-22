package com.hc.test.framework.core;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hc.test.framework.keywords.Test;

@Component
public class CustomFunctions {
	
//	private HashMap<String, HashMap<String, String>> params=new HashMap<String, HashMap<String,String>>();
	private HashMap<String, String> params=new HashMap<String, String>();
	private String url;
	
//	@Autowired
//	private PropertiesUtil properties;
	
//	@Autowired
//	private Test test;
	
//	@Autowired
//	private CustomFunctions custonFunctions;
	
	Test test=new Test();
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");

    }
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public boolean setParams(String data1,String data2) throws Exception{
    	
    	try{
    		logger.info("data1:"+data1);
    	
    		logger.info("data1:"+data2);
    		data1="url=http://172.16.1.113:8080/masterdata/v4/zip/";
    		data2="zipcode=560037";
			boolean funcOutput=test.setParams(data1,data2);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return false;
    	
    }

//	@Autowired
//	private PropertiesUtil properties;
//	
//	@Autowired
//	private UserAccessCodeTestDao userAccessCodeTestDao;
//    static {
//        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
//
//    }
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    
//	public HttpHeaders setHeaders(String appType) throws Exception{
//		try{
////			UserAccessCodeTestDao userAccessCodeTestDao = context.getBean(UserAccessCodeTestDao.class);
//		    HttpHeaders requestHeaders=new HttpHeaders();
//		    requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
////		    logger.info("userId:"+properties.getProperty("userId"));
//		    if(appType.equals("helpchat")){
////			    UserAccessCode userAccessCode=userAccessCodeTestDao.findLatestAccessCodeByUserId(Integer.parseInt(properties.getProperty("userId")));
//			    UserAccessCode userAccessCode=userAccessCodeTestDao.findLatestAccessCodeByUserId(305257);
//			    if(userAccessCode==null){
//			    	logger.error("Unable to find a valid access token for the user:"+properties.getProperty("userId"));
//			    	throw new Exception("No valid auth token found for user id:"+properties.getProperty("userId"));
//			    }		    
//			    requestHeaders.add("X-AKOSHA-AUTH",userAccessCode.getAkoshaAccessCode());
//			    logger.info("X_Akosha_Auth:"+userAccessCode.getAkoshaAccessCode());
//		    }
//		    
//		    return requestHeaders;
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new Exception (e.getMessage());
//			
//		}
//	}

}
