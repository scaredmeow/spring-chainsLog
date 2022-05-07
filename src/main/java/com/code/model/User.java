package com.code.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class User {
	private int user_id;
	private String email;
	private String username;
	private String password;
	private String confirmPassword;
	private String role;

	public User() {
	}

	public User(int user_id, 
			String email, 
			String username, 
			String password,
			String confirmPassword,
			String role) {
		this.user_id = user_id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.confirmPassword = confirmPassword;
	}

	public User(String email, 
			String username, 
			String password, 
			String confirmPassword) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	
	public User(String username, 
			String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(String email, 
			String username, 
			String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}


	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Users {user_id=" + user_id + 
				", email='" + email + "\'" +
				", username='" + username + "\'" +
				", password='" + password + "\'" +
				", role='" + role + "\'" +
				"}";
	}
	
	public List<String> getRoleList(){
		if(this.role.length() > 0) {
			return Arrays.asList(this.role.split(","));
		}
		return new ArrayList<>();
	}
	
}
