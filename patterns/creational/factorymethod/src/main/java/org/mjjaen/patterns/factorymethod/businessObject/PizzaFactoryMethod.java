package org.mjjaen.patterns.factorymethod.businessObject;

public class PizzaFactoryMethod extends BasePizzaFactoryMethod {
    @Override
	public  Pizza createPizza(TIPOPIZZA tipoPizza){
        Pizza pizza;
        switch (tipoPizza) {
            case CHEESE:
                pizza = new CheesePizza();
                break;
            case PEPPERONI:
                pizza = new PepperoniPizza();
                break;
            case VEGGIE:
                pizza = new VeggiePizza();
                break;
            default:
            	throw new IllegalArgumentException("No such pizza.");
        }
        pizza.addIngredients();
        pizza.bakePizza();
        return pizza;
    }
}
