package org.mjjaen.patterns.builder;

import org.mjjaen.patterns.builder.businessObject.ConcreteHouseBuilder;
import org.mjjaen.patterns.builder.businessObject.ConstructionEngineer;
import org.mjjaen.patterns.builder.businessObject.House;
import org.mjjaen.patterns.builder.businessObject.HouseBuilder;
import org.mjjaen.patterns.builder.businessObject.HouseImpl;
import org.mjjaen.patterns.builder.businessObject.PrefabricatedHouseBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuilderApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(BuilderApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		construcWithoutBuilder();
		construcWithBuilder();
	}
	
	public static void construcWithoutBuilder() {
		/*
		 * Empty constructor and then set methods
		 */
		House house = new HouseImpl();
		house.setFoundation("Concrete, brick, and stone");
		house.setRoof("Concrete and reinforced steel");
		house.setStructure("Concrete, mortar, brick, and reinforced steel");
		house.setFurnished(true);
		house.setPainted(true);
		System.out.println("House is: " + house);
		/*
		 * Constructor with parameters 1
		 */
		House house2 = new HouseImpl("Concrete, brick, and stone", "Concrete and reinforced steel", "Concrete, mortar, brick, and reinforced steel");
		house2.setFurnished(true);
		house2.setPainted(true);
		System.out.println("House is: " + house2);
		/*
		 * Constructor with parameters 2
		 */
		House house3 = new HouseImpl("Concrete, brick, and stone", "Concrete and reinforced steel", "Concrete, mortar, brick, and reinforced steel", true);
		house3.setPainted(true);
		System.out.println("House is: " + house3);
		/*
		 * Constructor with parameters 3
		 */
		House house4 = new HouseImpl("Concrete, brick, and stone", "Concrete and reinforced steel", "Concrete, mortar, brick, and reinforced steel", true, true);
		System.out.println("House is: " + house4);
	}
	
	public static void construcWithBuilder() {
		/*
		 * Concrete house builder
		 */
		HouseBuilder concreteHouseBuilder = new ConcreteHouseBuilder();
        ConstructionEngineer engineerA = new ConstructionEngineer(concreteHouseBuilder);
        House houseA = engineerA.constructHouse();
        System.out.println("House is: " + houseA);
        /*
		 * Prefabricated house builder
		 */
        PrefabricatedHouseBuilder prefabricatedHouseBuilder = new PrefabricatedHouseBuilder();
        ConstructionEngineer engineerB = new ConstructionEngineer(prefabricatedHouseBuilder);
        House houseB = engineerB.constructHouse();
        System.out.println("House is: " + houseB);
	}
}
