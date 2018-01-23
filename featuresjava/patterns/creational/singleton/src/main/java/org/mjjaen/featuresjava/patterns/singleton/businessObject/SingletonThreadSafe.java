package org.mjjaen.featuresjava.patterns.singleton.businessObject;

/*
 * Esta implementación es como la implementación perezosa (SingletonLazy) pero ofrece seguridad entre hilos,
 * asegurando que en una aplicación multihilo se respetará la sincronización
 */
public class SingletonThreadSafe {
	private static Car instance;
	
	private SingletonThreadSafe() {}
	
	/*
	 * Esta implementación reduce el rendimiento por el coste de la sincronización, que afecta a todo el método
	 * y afecta al rendimiento independientemente de si la instancia ya está creada o no.
	 */
	public static synchronized Car getInstance() {
		if(instance == null)
			instance = new CarImpl();
		return instance;
	}
	
	/*
	 * Este segundo método aumenta el rendimiento ya que se produce un doble check. El coste de la sincronización 
	 * sólo aplica si la instancia no está creada.
	 */
	public static synchronized Car getInstanceUsingDoubleChecking() {
		if(instance == null) {
			synchronized (Car.class) {
				if(instance == null)
					instance = new CarImpl();
			}
		}
		return instance;
	}
}
