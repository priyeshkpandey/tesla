package com.hc.test.framework.core;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeywordInvoker {

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}

	public static Logger LOGGER = LoggerFactory.getLogger(KeywordInvoker.class);

	public boolean invokeKeyword(AppTypes appType, HashMap params) {
		boolean isInvoked = false;

		try {
			Class c = Class.forName("com.hc.test.framework.keywords."
					+ appType.keywordClass());

			// TODO: Invocation of keywords and signature specification for
			// keywords

		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
		}

		return isInvoked;
	}

}
