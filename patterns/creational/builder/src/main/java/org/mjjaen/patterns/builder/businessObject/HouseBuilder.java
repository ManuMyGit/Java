package org.mjjaen.patterns.builder.businessObject;

public interface HouseBuilder {
	public void buildFoundation();
	public void buildStructure();
	public void buildRoof();
	public void paintHouse();
	public void furnishHouse();
	public House getHouse();
}
