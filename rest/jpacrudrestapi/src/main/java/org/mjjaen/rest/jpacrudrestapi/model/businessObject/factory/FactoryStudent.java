package org.mjjaen.rest.jpacrudrestapi.model.businessObject.factory;

import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Student;

public class FactoryStudent {
	public static Student createStudent() {
		return new Student();
	}
}
