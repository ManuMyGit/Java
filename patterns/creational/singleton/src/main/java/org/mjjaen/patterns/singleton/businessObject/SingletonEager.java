package org.mjjaen.patterns.singleton.businessObject;

/*
 * Esta implementación de Singleton se puede usar si instance no consume muchos recursos y no se esperan excepciones
 */
public class SingletonEager {
	/*
	 * Se instancia la clase de forma pronta, en el momento que se crea SingletonEager
	 */
	private static final Car instance = new CarImpl();
	
	private SingletonEager() {}
	
	public static Car getInstance() {
		return instance;
	}
}
