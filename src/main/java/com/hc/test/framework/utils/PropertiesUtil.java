package com.hc.test.framework.utils;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertiesUtil {

	@Autowired
	private Properties properties;

	public String getProperty(String key) {

		return properties.getProperty(key);
	}
	
}
