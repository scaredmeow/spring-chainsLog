package com.code.service;


import com.code.model.User;

public interface AuthService {

	public boolean login(String username, String password);
	public User signup(User user);
	public void forgetPass (String email); 
}
