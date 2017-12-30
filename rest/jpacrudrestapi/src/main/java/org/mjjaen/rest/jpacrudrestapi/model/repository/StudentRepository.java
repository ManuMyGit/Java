package org.mjjaen.rest.jpacrudrestapi.model.repository;

import java.util.List;
import java.util.Optional;

import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Student;
import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Integer>, StudentRepositoryCustom<Student, Integer> {
	@Query("select e from Student e where e.teacher = :teacher")
	public List<Student> findByTeacher(@Param("teacher") Teacher teacher);
	
	@Query("select e from Student e where e.teacher = :teacher and e.id = :id")
	public Optional<Student> findByIdAndTeacherId(@Param("id") Integer id, @Param("teacher") Teacher teacher);
}
