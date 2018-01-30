package org.mjjaen.patterns.factorymethod;

import org.mjjaen.patterns.factorymethod.businessObject.BasePizzaFactoryMethod;
import org.mjjaen.patterns.factorymethod.businessObject.Pizza;
import org.mjjaen.patterns.factorymethod.businessObject.PizzaFactoryMethod;
import org.mjjaen.patterns.factorymethod.businessObject.PizzaSimpleFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FactorymethodApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(FactorymethodApplication.class, args);
	}

	@SuppressWarnings("unused")
	@Override
	public void run(String... args) throws Exception {
		BasePizzaFactoryMethod pizzaFactory = new PizzaFactoryMethod();
        Pizza cheesePizza = pizzaFactory.createPizza(BasePizzaFactoryMethod.TIPOPIZZA.CHEESE);
        Pizza veggiePizza = pizzaFactory.createPizza(BasePizzaFactoryMethod.TIPOPIZZA.VEGGIE);
        
        Pizza cheesePizza2 = PizzaSimpleFactory.createPizza("cheese");
        cheesePizza2.addIngredients();
        cheesePizza2.bakePizza();
        Pizza veggiePizza2 = PizzaSimpleFactory.createPizza("veggie");
        veggiePizza2.addIngredients();
        veggiePizza2.bakePizza();
	}
}
