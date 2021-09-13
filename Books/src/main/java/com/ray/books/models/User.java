package com.ray.books.models;

public class User {
	private String username;
	private String password;
	private int decentral_id;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDecentral_id() {
		return decentral_id;
	}
	public void setDecentral_id(int decentral_id) {
		this.decentral_id = decentral_id;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", decentral_id=" + decentral_id + "]";
	}
		

}
