package ua.nure.sidorovich.entety;

public class HotelType {
	private long id;
	private int hotelClass;
	private String description;
	
	public HotelType() {
		super();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
		
	public int getHotelClass() {
		return hotelClass;
	}

	public void setHotelClass(int hotelType) {
		this.hotelClass = hotelType;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "HotelType [id=" + id + ", hotelClass=" + hotelClass + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + hotelClass;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		HotelType other = (HotelType) obj;
		if (description == null) {
			if (other.description != null){
				return false;
			}
		} else if (!description.equals(other.description)){
			return false;
		}
		if (hotelClass != other.hotelClass){
			return false;
		}
		if (id != other.id){
			return false;
		}
		return true;
	}
	
	
	
}
