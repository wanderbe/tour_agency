package ua.nure.sidorovich.entety;

public class OrderStatus {
	private long id;
	private String name;
	private String description;
	
	public OrderStatus() {
		super();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		OrderStatus other = (OrderStatus) obj;
		if (id != other.id){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "OrderStatus [id=" + id + ", name=" + name + ", description=" + description + "]";
	}	
}
