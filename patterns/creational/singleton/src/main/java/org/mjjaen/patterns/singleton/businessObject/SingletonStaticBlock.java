package org.mjjaen.patterns.singleton.businessObject;

/*
 * Similar a SingletonEager, con la diferencia de que el objeto instance se crea en un bloque estático que permite
 * gestionar excepciones
 */
public class SingletonStaticBlock {
	private static Car instance;
	
	static {
		try {
			instance = new CarImpl();
		} catch (Exception e) {
			throw new RuntimeException("Error during creation");
		}
	}
	
	private SingletonStaticBlock() {}
	
	public static Car getInstance() {
		return instance;
	}
}
