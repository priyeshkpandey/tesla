package com.hc.test.framework.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class TestFrameworkService {

	@RequestMapping(method = RequestMethod.POST, value="/init/test")
	public ResponseEntity<?> initiateTest(HttpServletRequest request)
	{
		String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
		
		if(ipAddr == null)
		{
			ipAddr = request.getRemoteAddr();
		}
		
		System.out.println("Client IP: "+ipAddr);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
