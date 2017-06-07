package ua.nure.sidorovich.entety;

public class Tour {
	private long id;
	private String name;
	private String description;
	private RestType restType;
	private int places;
	private Hotel hotel;
	private double price; // todo
	private boolean isItHot;

	public Tour() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isItHot() {
		return isItHot;
	}

	public void setItHot(boolean isItHot) {
		this.isItHot = isItHot;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double cost) {
		this.price = cost;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int place) {
		this.places = place;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public RestType getRestType() {
		return restType;
	}

	public void setRestType(RestType restType) {
		this.restType = restType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Tour other = (Tour) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Tour [id=" + id + ", isItHot=" + isItHot + ", name=" + name + ", description=" + description
				+ ", restType=" + restType + ", cost=" + price + ", place=" + places + ", hotel=" + hotel + "]";
	}
}
