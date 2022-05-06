package com.code.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.code.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUserName(String username) {
		String sql = "SELECT * FROM users WHERE username = '" + username + "'";
		return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
	}

	@Override
	public int saveUserRegistration(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> getAllPosts(int UID) {
		// TODO Auto-generated method stub
		return null;
	}

}
