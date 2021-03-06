package com.hc.test.framework.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hc.test.framework.core.AppTypes;
import com.hc.test.framework.core.ExecutionEngine;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


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
	public ResponseEntity<?> initiateTest(@Context HttpServletRequest httpServletRequest
			,@RequestHeader(value="User-Agent") String osType,
         @RequestHeader String buildpath,@RequestParam("env") String env, @RequestParam("appType") AppTypes appType)
	{
        Long startTime= Calendar.getInstance().getTimeInMillis();
		try{
		LOGGER.info("Execution started");


		String ipAddr=httpServletRequest.getRemoteAddr();
		String os = null;
		LOGGER.info(ipAddr);
		
		if (osType.toLowerCase().indexOf("windows") >= 0 )
        {
            os = "Win";
        } else if(osType.toLowerCase().indexOf("mac") >= 0)
        {
            os = "Mac";
        
        }else if(osType.toLowerCase().indexOf("linux")>=0){
			os="Linux";
		}
		else{
            os = "UnKnown, More-Info: "+osType;
			LOGGER.warn(os);
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

		execEngine.mainFlow(ipAddr, os,buildpath);
            Long endTime=Calendar.getInstance().getTimeInMillis();
		return new ResponseEntity<Object>("Execution finished Successfully"+"\n"+"Total time taken:"+
                TimeUnit.MILLISECONDS.toSeconds(endTime-startTime)+" Seconds"
                ,HttpStatus.OK);
		
		}catch(Exception e){
			e.printStackTrace();
            return new ResponseEntity<Object>("Error occured during execution." +
                    "Please check stacktrace information"+"\n\n"+ExceptionUtils.getStackTrace(e),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
