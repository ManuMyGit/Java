package org.mjjaen.featuresjava.patterns.singleton;

import org.mjjaen.featuresjava.patterns.singleton.businessObject.Car;
import org.mjjaen.featuresjava.patterns.singleton.businessObject.CarFactory;
import org.mjjaen.featuresjava.patterns.singleton.businessObject.SingletonImplementation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SingletonApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(SingletonApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		Car car1 = CarFactory.createCar(SingletonImplementation.SINGLETONLAZY);
		car1.setBrand("Seat");
		car1.setModel("Ibiza");
		System.out.println("Car 1 is created");
		System.out.println(car1);
		Car car2 = CarFactory.createCar(SingletonImplementation.SINGLETONLAZY);
		System.out.println("Car 2 is created. This car is equal to car 1");
		System.out.println(car2);
		car1.setBrand("BMW");
		car1.setModel("Serie 3");
		System.out.println("Car 1 is modified and car 2 is printed. You can see that car 2 is modified because car 2 is the same than car 1");
		System.out.println(car2);
	}
}
