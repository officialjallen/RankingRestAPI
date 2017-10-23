package com.jallen.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to hold the common metric data
 * @author Jonathan Allen
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MetricContents {
	@JsonProperty("walkability")
	private float walkability;
	@JsonProperty("job_growth")
	private float jobGrowth;
	@JsonProperty("green_space")
	private float greenSpace;
	@JsonProperty("taxes")
	private float taxes;
	
	public float getWalkability() {
		return walkability;
	}
	public void setWalkability(float walkability) {
		this.walkability = walkability;
	}
	public float getJobGrowth() {
		return jobGrowth;
	}
	public void setJobGrowth(float jobGrowth) {
		this.jobGrowth = jobGrowth;
	}
	public float getGreenSpace() {
		return greenSpace;
	}
	public void setGreenSpace(float greenSpace) {
		this.greenSpace = greenSpace;
	}
	public float getTaxes() {
		return taxes;
	}
	public void setTaxes(float taxes) {
		this.taxes = taxes;
	}
}
