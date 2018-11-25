package org.mjjaen.rest.jpacrudrestapi.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.mjjaen.rest.jpacrudrestapi.model.businessObject.Student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"createdAt", "updatedAt"})
public class TeacherDto implements Serializable {
	private static final long serialVersionUID = -203752711321943176L;
	
	private Integer id;
	private Integer version;
	@NotNull
	@Size(min=1, max=64)
	private String name;
	@NotNull(message = "{email.notnull}") //Personalización del mensaje de error, está en ValidationMessages.properties
	@Size(min=1, max=128)
	private String email;
	@NotNull
	@Past
	private Date birthDate;
    private Date createdAt;
    private Date updatedAt;
    
    private List<Student> students;
	
	public TeacherDto() {}
	
	public TeacherDto(Integer id, Integer version, String name, String email, Date birthDate, Date createdAt, Date updatedAt, List<Student> students) {
		super();
		this.id = id;
		this.version = version;
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.students = students;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeacherDto other = (TeacherDto) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
