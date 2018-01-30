package org.mjjaen.patterns.factorymethod.businessObject;

public class CheesePizza extends Pizza {
	@Override
    public void addIngredients() {
        System.out.println("Preparing ingredients for cheese pizza.");
    }
}
