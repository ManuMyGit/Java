package org.mjjaen.rest.apidocument.model.repository.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mjjaen.rest.apidocument.model.businessObject.Teacher;
import org.mjjaen.rest.apidocument.model.repository.TeacherRepositoryCustom;

public class TeacherRepositoryCustomImpl<T extends Teacher, ID extends Serializable> implements TeacherRepositoryCustom <T, ID> {
	@PersistenceContext
	private EntityManager entityManager;
	private Class<T> domainClass;
	
	public TeacherRepositoryCustomImpl(Class<T> domainClass) {
    	this.setDomainClass(domainClass);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Class<T> getDomainClass() {
		return domainClass;
	}

	public void setDomainClass(Class<T> domainClass) {
		this.domainClass = domainClass;
	}
}
