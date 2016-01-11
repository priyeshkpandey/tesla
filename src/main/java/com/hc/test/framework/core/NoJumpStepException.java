package com.hc.test.framework.core;

public class NoJumpStepException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getMessage()
	{
		return "No jump statement, break in case of failure.";
	}

}
