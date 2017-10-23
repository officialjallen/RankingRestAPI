package com.jallen.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to hold common response data
 * @author Jonathan Allen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityResponse {
	@JsonProperty("name")
	private String cityName;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("id")
	private String cityId;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("scores")
	private MetricContents metricContents;
	private String Error;
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public MetricContents getMetricContents() {
		return metricContents;
	}

	public void setMetricContents(MetricContents metricContents) {
		this.metricContents = metricContents;
	}

	public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}
}
