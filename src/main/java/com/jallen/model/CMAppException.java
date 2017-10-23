package com.jallen.model;

/**
 * Custom error for application specific errors
 * @author Jonathan Allen
 */
public class CMAppException extends Exception {
	private static final long serialVersionUID = -5595815051892161207L;

	public CMAppException(String message) {
		super(message);
	}

}
