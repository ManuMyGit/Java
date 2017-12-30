package org.mjjaen.rest.secutiry.model.businessObject;

public class SecurityBean {
	private String name;
	
	public SecurityBean() {
		super();
		this.name = "This is an example of basic authentication";
	}
	
	public SecurityBean(String name) {
		super();
		this.name = "Hello World ".concat(name).concat("!");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		SecurityBean other = (SecurityBean) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
