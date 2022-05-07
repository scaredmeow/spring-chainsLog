package com.code.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.code.model.User;

@Component
public interface UserDao {
	
	public User findByUserName(String username);
	public List<User> getAllUsers();
	public List<User> getAllPosts(int UID);
	public boolean saveUserRegistration(User user);
	public boolean existsEmail(String email);
	public boolean existsUsername(String username);
	
}
