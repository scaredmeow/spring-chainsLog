package com.code.service;

import org.springframework.stereotype.Service;

import com.code.model.User;

@Service
public class AuthServiceImpl implements AuthService {

	@Override
	public User signup(User user) {
		user.setUser_id(2);
		user.setConfirmPassword("Hello");
		return user;
	}

	@Override
	public boolean login(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void forgetPass(String email) {
		// TODO Auto-generated method stub
		
	}

}
