package ua.nure.sidorovich.entety;

import java.sql.Date;

public class User {
	private long id;
	private String name;
	private Date birthday;
	private String email;
	private String phone;
	private String login;
	private String password;
	private UserRole role;
	
	public User() {
		super();
	}
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthday=" + birthday + ", email=" + email + ", phone=" + phone
				+ ", login=" + login + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		User other = (User) obj;
		if (birthday == null) {
			if (other.birthday != null){
				return false;
			}
		} else if (!birthday.equals(other.birthday)){
			return false;
		}
		if (email == null) {
			if (other.email != null){
				return false;
			}
		} else if (!email.equals(other.email)){
			return false;
		}
		if (id != other.id){
			return false;
		}
		if (login == null) {
			if (other.login != null){
				return false;
			}
		} else if (!login.equals(other.login)){
			return false;
		}
		if (name == null) {
			if (other.name != null){
				return false;
			}
		} else if (!name.equals(other.name)){
			return false;
		}
		if (phone == null) {
			if (other.phone != null){
				return false;
			}
		} else if (!phone.equals(other.phone)){
			return false;
		}
		if (role == null) {
			if (other.role != null){
				return false;
			}
		} else if (!role.equals(other.role)){
			return false;
		}
		return true;
	}	
	
	
	
}
