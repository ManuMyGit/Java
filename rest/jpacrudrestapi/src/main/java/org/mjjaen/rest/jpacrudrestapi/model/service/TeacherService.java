package org.mjjaen.rest.jpacrudrestapi.model.service;

import java.util.List;
import java.util.Optional;

import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Teacher;

public interface TeacherService {
	public List<Teacher> findAll(boolean order, String field);
	public Optional<Teacher> findOneTeacher(Integer id);
	public Teacher save(Teacher teacher);
	public Teacher update(Teacher teacher, Integer id);
	public Optional<Teacher> delete(Integer id);
}
