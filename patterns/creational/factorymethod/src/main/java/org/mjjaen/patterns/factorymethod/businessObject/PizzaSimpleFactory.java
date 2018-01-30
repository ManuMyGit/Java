package org.mjjaen.patterns.factorymethod.businessObject;

public class PizzaSimpleFactory {
	public static Pizza createPizza(String type){
        Pizza pizza;
        switch (type.toLowerCase()) {
            case "cheese":
                pizza = new CheesePizza();
                break;
            case "pepperoni":
                pizza = new PepperoniPizza();
                break;
            case "veggie":
                pizza = new VeggiePizza();
                break;
            default:
            	throw new IllegalArgumentException("No such pizza.");
        }
        return pizza;
	}
}
