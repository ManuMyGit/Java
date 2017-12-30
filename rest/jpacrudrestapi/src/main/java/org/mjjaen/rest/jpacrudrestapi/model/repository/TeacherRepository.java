package org.mjjaen.rest.jpacrudrestapi.model.repository;

import java.util.List;

import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
	public List<Teacher> findAllByOrderByNameAsc();
	public List<Teacher> findAllByOrderByEmailAsc();
	public List<Teacher> findAllByOrderByBirthDateAsc();
	public List<Teacher> findAllByOrderByIdAsc();
}
