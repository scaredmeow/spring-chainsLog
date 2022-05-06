package com.code.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.code.model.User;

@Component
public interface UserDao {
	
	public User findByUserName(String username);
	public int saveUserRegistration(User user);
	public List<User> getAllUsers();
	public List<User> getAllPosts(int UID);
	
}
