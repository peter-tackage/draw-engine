package com.moac.drawengine;

@SuppressWarnings("serial")
public class InvalidDrawEngineException extends Exception {

	public InvalidDrawEngineException(String string, Exception e) {
		super(string, e);
	}

}
