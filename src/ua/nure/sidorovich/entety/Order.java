package ua.nure.sidorovich.entety;

import java.sql.Date;

public class Order {
	
	private long id;
	private Date registerTime;
	private User user;
	private Tour tour;	
	private double cost;
	private OrderStatus orderStatus;
	
	public Order() {
		super();
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Date getRegisterTime() {
		return registerTime;
	}


	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", registerTime=" + registerTime + ", user=" + user + ", tour=" + tour + ", cost="
				+ cost + ", orderStatus=" + orderStatus + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result + ((registerTime == null) ? 0 : registerTime.hashCode());
		result = prime * result + ((tour == null) ? 0 : tour.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Order other = (Order) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost)){
			return false;
		}
		if (id != other.id){
			return false;
		}
		if (orderStatus == null) {
			if (other.orderStatus != null){
				return false;
			}
		} else if (!orderStatus.equals(other.orderStatus)){
			return false;
		}
		if (registerTime == null) {
			if (other.registerTime != null){
				return false;
			}
		} else if (!registerTime.equals(other.registerTime)){
			return false;
		}
		if (tour == null) {
			if (other.tour != null){
				return false;
			}
		} else if (!tour.equals(other.tour)){
			return false;
		}
		if (user == null) {
			if (other.user != null){
				return false;
			}
		} else if (!user.equals(other.user)){
			return false;
		}
		return true;
	}	
	
	
}
