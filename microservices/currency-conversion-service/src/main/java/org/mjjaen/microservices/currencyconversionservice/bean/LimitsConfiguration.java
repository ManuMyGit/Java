package org.mjjaen.microservices.currencyconversionservice.bean;

public class LimitsConfiguration {
	private Integer maximum;
	private Integer minimum;
	
	public LimitsConfiguration() {
		super();
	}
	
	public LimitsConfiguration(Integer maximum, Integer minimum) {
		super();
		this.maximum = maximum;
		this.minimum = minimum;
	}
	
	public Integer getMaximum() {
		return maximum;
	}
	
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
	
	public Integer getMinimum() {
		return minimum;
	}
	
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	
	@Override
	public String toString() {
		return "Maximum: " + this.maximum + " -> Minimum: " + this.minimum;
	}
}
