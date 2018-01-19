package org.mjjaen.functionalprogramming;

public class Apple {
	private String color;
	private Integer peso;
	private Integer precio;
	
	public Apple() {}
	
	public Apple(String color, Integer peso) {
		this.color = color;
		this.peso = peso;
	}
	
	public Apple(String color, Integer peso, Integer precio) {
		this.color = color;
		this.peso = peso;
		this.precio = precio;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		String cadena = "";
		cadena += "Color: " + this.color + "\n";
		cadena += "Peso: " + this.peso + "\n";
		return cadena;
	}
}
