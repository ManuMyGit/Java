package org.mjjaen.microservices.limitsservice.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="limits-service")
public class Configuration {
	private Integer minimum;
	private Integer maximum;
	
	public Configuration() {
		super();
	}

	public Configuration(Integer minimum, Integer maximum) {
		super();
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public Integer getMinimum() {
		return minimum;
	}
	
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	
	public Integer getMaximum() {
		return maximum;
	}
	
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
}
