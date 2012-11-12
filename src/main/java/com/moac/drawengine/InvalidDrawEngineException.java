package com.moac.drawengine;

@SuppressWarnings("serial")
public class InvalidDrawEngineException extends Exception {

	public InvalidDrawEngineException(String msg, Exception e) {
		super(msg, e);
	}

}
