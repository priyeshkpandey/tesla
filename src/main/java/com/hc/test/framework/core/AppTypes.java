package com.hc.test.framework.core;

public enum AppTypes {
	
	ANDROID("AndroidKeywords"),
	WEB("WebKeywords"),
	SERVICE("ServiceKeywords"),
	IOS("IOSKeywords");
	
	private final String keywordClass;
	
	AppTypes(String keywordClass)
	{
		this.keywordClass = keywordClass;
	}

	public String keywordClass(){return keywordClass;}
}
