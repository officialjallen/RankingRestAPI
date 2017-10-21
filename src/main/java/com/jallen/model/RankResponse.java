package com.jallen.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RankResponse extends CityResponse{
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("overall_score")
	private String calculatedScore;

	public String getCalculatedScore() {
		return calculatedScore;
	}

	public void setCalculatedScore(String calculatedScore) {
		this.calculatedScore = calculatedScore;
	}

}
