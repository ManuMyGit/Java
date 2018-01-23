package org.mjjaen.patterns.singleton.businessObject;

public class CarImpl implements Car {
	private String brand;
	private String model;
	
	public CarImpl() {
		super();
	}
	
	public CarImpl(String brand, String model) {
		super();
		this.brand = brand;
		this.model = model;
	}
	
	public String getBrand() {
		return this.brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getModel() {
		return this.model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	@Override
	public String toString() {
		String cadena = "";
		if(this.brand != null && !this.brand.equals(""))
			cadena += "Brand: " + this.brand + "\n";
		if(this.model != null && !this.model.equals(""))
			cadena += "Model: " + this.model + "\n";
		return cadena;
	}
}