package com.hc.test.framework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hc.test.framework.core.AppTypes;
import com.hc.test.framework.core.ExecutionEngine;




@RestController
public class TestFrameworkService {
	
	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
		System.out.println("test");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(TestFrameworkService.class);
	
	@Autowired
	ExecutionEngine execEngine;

	@RequestMapping(method = RequestMethod.GET, value="/init/test")
	public ResponseEntity<?> initiateTest(@RequestHeader(value="User-Agent") String osType,
			@RequestHeader(value="REMOTE_ADDR") String ipAddr,
			@RequestParam("env") String env, @RequestParam("appType") AppTypes appType)
	{
		try{
		System.out.println("Execution started");
		
		
		
		String os = null;
		System.out.println(ipAddr);
		
		if (osType.toLowerCase().indexOf("windows") >= 0 )
        {
            os = "Win";
        } else if(osType.toLowerCase().indexOf("mac") >= 0)
        {
            os = "Mac";
        
        }else{
            os = "UnKnown, More-Info: "+osType;
        }
		
		if(env != null)
		{
			execEngine.setEnv(env);
		}
		
		if(appType != null)
		{
			execEngine.setIsOnlyAppType(true);
			execEngine.setAppType(appType);
		}
		System.out.println(os);
		execEngine.mainFlow(ipAddr, os);
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
