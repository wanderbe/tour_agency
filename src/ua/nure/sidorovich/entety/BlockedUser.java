package ua.nure.sidorovich.entety;

import java.sql.Timestamp;

public class BlockedUser {
	
	private User user;
	private Timestamp blockTime;
	private String description;
	
	public BlockedUser() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(Timestamp blockTime) {
		this.blockTime = blockTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "BlockedUser [user=" + user + ", blockTime=" + blockTime + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blockTime == null) ? 0 : blockTime.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		BlockedUser other = (BlockedUser) obj;
		if (blockTime == null) {
			if (other.blockTime != null){
				return false;
			}
		} else if (!blockTime.equals(other.blockTime)){
			return false;
		}
		if (description == null) {
			if (other.description != null){
				return false;
			}
		} else if (!description.equals(other.description)){
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
