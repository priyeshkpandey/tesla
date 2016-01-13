package com.hc.test.framework.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hc.test.framework.core.AppTypes;
import com.hc.test.framework.core.ExecutionEngine;




@Controller
public class TestFrameworkService {
	
	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory
			.getLogger(TestFrameworkService.class);
	
	@Autowired
	ExecutionEngine execEngine;

	@RequestMapping(method = RequestMethod.POST, value="/init/test")
	public ResponseEntity<?> initiateTest(HttpServletRequest request,
			@RequestParam("env") String env, @RequestParam("appType") AppTypes appType)
	{
		String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
		
		if(ipAddr == null)
		{
			ipAddr = request.getRemoteAddr();
		}
		
		String osType = request.getHeader("User-Agent");
		String os = null;
		
		if (osType.toLowerCase().indexOf("windows") >= 0 )
        {
            os = "Win";
        } else if(osType.toLowerCase().indexOf("mac") >= 0)
        {
            os = "Mac";
        } else if(osType.toLowerCase().indexOf("x11") >= 0)
        {
            os = "Unix";
        } else if(osType.toLowerCase().indexOf("android") >= 0)
        {
            os = "Android";
        } else if(osType.toLowerCase().indexOf("iphone") >= 0)
        {
            os = "IPhone";
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
		
		execEngine.mainFlow(ipAddr, os);
		
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
