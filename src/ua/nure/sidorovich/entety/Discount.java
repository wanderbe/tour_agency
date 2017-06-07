package ua.nure.sidorovich.entety;

import java.sql.Timestamp;

public class Discount {
	
	private long id;
	private Timestamp registerTime;
	private int step;
	private int maxPercent;	
	
	public Discount() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getMaxPercent() {
		return maxPercent;
	}

	public void setMaxPercent(int maxPercent) {
		this.maxPercent = maxPercent;
	}

	@Override
	public String toString() {
		return "Discount [id=" + id + ", registerTime=" + registerTime + ", step=" + step + ", maxPercent=" + maxPercent
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + maxPercent;
		result = prime * result + ((registerTime == null) ? 0 : registerTime.hashCode());
		result = prime * result + step;
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
		Discount other = (Discount) obj;
		if (id != other.id){
			return false;
		}
		if (maxPercent != other.maxPercent){
			return false;
		}
		if (registerTime == null) {
			if (other.registerTime != null){
				return false;
			}
		} else if (!registerTime.equals(other.registerTime)){
			return false;
		}
		if (step != other.step){
			return false;
		}
		return true;
	}
	
	
	
}
