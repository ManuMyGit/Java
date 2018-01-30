package org.mjjaen.patterns.factorymethod.businessObject;

public class PepperoniPizza extends Pizza {
	@Override
    public void addIngredients() {
        System.out.println("Preparing ingredients for pepperoni pizza.");
    }
}
