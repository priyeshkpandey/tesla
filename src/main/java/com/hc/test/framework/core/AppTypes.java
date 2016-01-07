package com.hc.test.framework.core;

public enum AppTypes {
	
	ANDROID("AndroidKeywords","ANDROID"),
	WEB("WebKeywords","WEBDRIVER"),
	SERVICE("ServiceKeywords","SERVICE"),
	IOS("IOSKeywords","IOS");
	
	private final String keywordClass;
	private final String appName;
	
	AppTypes(String keywordClass, String appName)
	{
		this.keywordClass = keywordClass;
		this.appName = appName;
	}

	public String keywordClass(){return keywordClass;}
	public String appName(){return appName;}
}
