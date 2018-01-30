package org.mjjaen.patterns.builder.businessObject;

public class HouseImpl implements House {
	private String foundation;
	private String structure;
	private String roof;
	private boolean painted;
	private boolean furnished;
	
	public HouseImpl() {
		super();
	}
	
	public HouseImpl(String foundation, String structure, String roof) {
		super();
		this.foundation = foundation;
		this.structure = structure;
		this.roof = roof;
	}
	
	public HouseImpl(String foundation, String structure, String roof, boolean painted) {
		super();
		this.foundation = foundation;
		this.structure = structure;
		this.roof = roof;
		this.painted = painted;
	}
	
	public HouseImpl(String foundation, String structure, String roof, boolean painted, boolean furnished) {
		super();
		this.foundation = foundation;
		this.structure = structure;
		this.roof = roof;
		this.painted = painted;
		this.furnished = furnished;
	}
	
	public String getFoundation() {
		return foundation;
	}
	
	public void setFoundation(String foundation) {
		this.foundation = foundation;
	}
	
	public String getStructure() {
		return structure;
	}
	
	public void setStructure(String structure) {
		this.structure = structure;
	}
	
	public String getRoof() {
		return roof;
	}
	
	public void setRoof(String roof) {
		this.roof = roof;
	}
	
	public boolean isPainted() {
		return painted;
	}
	
	public void setPainted(boolean painted) {
		this.painted = painted;
	}
	
	public boolean isFurnished() {
		return furnished;
	}
	
	public void setFurnished(boolean furnished) {
		this.furnished = furnished;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((foundation == null) ? 0 : foundation.hashCode());
		result = prime * result + (furnished ? 1231 : 1237);
		result = prime * result + (painted ? 1231 : 1237);
		result = prime * result + ((roof == null) ? 0 : roof.hashCode());
		result = prime * result + ((structure == null) ? 0 : structure.hashCode());
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
		HouseImpl other = (HouseImpl) obj;
		if (foundation == null) {
			if (other.foundation != null)
				return false;
		} else if (!foundation.equals(other.foundation))
			return false;
		if (furnished != other.furnished)
			return false;
		if (painted != other.painted)
			return false;
		if (roof == null) {
			if (other.roof != null)
				return false;
		} else if (!roof.equals(other.roof))
			return false;
		if (structure == null) {
			if (other.structure != null)
				return false;
		} else if (!structure.equals(other.structure))
			return false;
		return true;
	}
	
	@Override
    public String toString() {
        return "Foundation - " + foundation + " Structure - " + structure + " Roof - " + roof + " Is Furnished? " + furnished + " Is Painted? " + painted;
    }
}
