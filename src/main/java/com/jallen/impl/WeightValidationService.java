package com.jallen.impl;

import com.jallen.model.CMAppException;
import com.jallen.model.RankRequest;

/**
 * Basic Weight Validation
 * @author Jonathan Allen
 */
public class WeightValidationService {
	/**
	 * Validate the entered weights
	 * @param request         The request that contains the weights
	 * @throws CMAppException Thrown when one of the weights is invalid
	 */
	public static void validateWeights(RankRequest request) throws CMAppException {
		if (request.getMetricContents().getGreenSpace() < 0) {
			throw new CMAppException("Invalid Greenspace Weight Entered, Must Be Positive Number or Zero");
		}
		if (request.getMetricContents().getJobGrowth() < 0) {
			throw new CMAppException("Invalid Job Growth Weight Entered, Must Be Positive Number or Zero");
		}
		if (request.getMetricContents().getTaxes() < 0) {
			throw new CMAppException("Invalid Taxes Weight Entered, Must Be Positive Number or Zero");
		}
		if (request.getMetricContents().getWalkability() < 0) {
			throw new CMAppException("Invalid Walkability Weight Entered, Must Be Positive Number or Zero");
		}
		return;		
	}
}
