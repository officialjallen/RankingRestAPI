package com.jallen.impl;

import com.jallen.model.CMAppException;
import com.jallen.model.CityResponse;

public class IdValidationService {
		
	public static void ValidateId(int cityId, CityResponse response) throws CMAppException {
		if (cityId <= 0) {
			throw new CMAppException("Invalid City Id Entered, Must Be Numeric");
		}
		return;		
	}
}
