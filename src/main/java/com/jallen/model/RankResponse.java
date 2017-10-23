package com.jallen.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object that extends the common city response data with ranked city list score
 * @author Jonathan Allen
 */
public class RankResponse extends CityResponse {
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("overall_score")
	private float calculatedScore;

	public float getCalculatedScore() {
		return calculatedScore;
	}

	public void setCalculatedScore(float calculatedScore) {
		this.calculatedScore = calculatedScore;
	}
}
