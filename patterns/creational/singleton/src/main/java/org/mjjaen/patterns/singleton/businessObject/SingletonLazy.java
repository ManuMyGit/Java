package org.mjjaen.patterns.singleton.businessObject;

/*
 * Esta implementación se utiliza si instance consume mucha memoria y no se tiene que crear hasta que sea necesario
 */
public class SingletonLazy {
	private static Car instance;
	
	private SingletonLazy() {}
	
	public static Car getInstance() {
		if(instance == null)
			instance = new CarImpl();
		return instance;
	}
}
