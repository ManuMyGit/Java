package org.mjjaen.patterns.factorymethod.businessObject;

public abstract class BasePizzaFactoryMethod {
	public enum TIPOPIZZA {CHEESE, PEPPERONI, VEGGIE};
	public abstract Pizza createPizza(TIPOPIZZA tipoPizza);
}
