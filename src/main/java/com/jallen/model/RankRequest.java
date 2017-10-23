package com.jallen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to hold the city rank request data
 * @author Jonathan Allen
 */
public class RankRequest {
	@JsonProperty("weights")
	private MetricContents metricContents;
	
	public MetricContents getMetricContents() {
		return metricContents;
	}

	public void setMetricContents(MetricContents metricContents) {
		this.metricContents = metricContents;
	}
}