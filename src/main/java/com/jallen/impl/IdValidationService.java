package com.jallen.impl;

import com.jallen.model.CMAppException;

/**
 * Simple ID validation
 * @author Jonathan Allen
 */
public class IdValidationService {
	/**
	 * Validate the entered ID
	 * @param cityId          The entered city id
	 * @return                Validated city number
	 * @throws CMAppException Thrown when the id is invalid
	 */
	public static int validateId(String cityId) throws CMAppException {
		int cityIdNumber = 0;	
		try {
			cityIdNumber = Integer.parseInt(cityId);
		} catch(NumberFormatException e) {
			throw new CMAppException("Invalid City Id Entered, Must Be an Integer");
		}
		if (cityIdNumber <= 0) {
			throw new CMAppException("Invalid City Id Entered, Must Be a Positive Integer");
		}
		return cityIdNumber;		
	}
}
