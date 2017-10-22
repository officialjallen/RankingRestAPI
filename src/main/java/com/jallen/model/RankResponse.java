package com.jallen.model;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
