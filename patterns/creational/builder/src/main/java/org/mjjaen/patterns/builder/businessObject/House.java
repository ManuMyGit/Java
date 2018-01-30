package org.mjjaen.patterns.builder.businessObject;

public interface House {
	public String getFoundation();
	public void setFoundation(String foundation);
	public String getStructure();
	public void setStructure(String structure);
	public String getRoof();
	public void setRoof(String roof);
	public boolean isPainted();
	public void setPainted(boolean painted);
	public boolean isFurnished();
	public void setFurnished(boolean furnished);
}
