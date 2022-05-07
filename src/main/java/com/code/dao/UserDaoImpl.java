package com.code.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.code.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	@Override
	public List<User> getAllUsers() {
		String sql="SELECT * FROM users";
		List<User> listOfUsers = jdbcTemplate.query(sql, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUser_id(rs.getInt("user_id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setRole(rs.getString("role"));
				return user;
			}
		});
		return listOfUsers;
	}

	@Override
	public User findByUserName(String username) {
		String sql = "SELECT * FROM users WHERE username = '" + username + "'";
		return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
	}

	@Override
	public boolean saveUserRegistration(User user) {
		String sql = "INSERT INTO users(email,username,password,role) VALUES(?, ?, ?, ?)";
		return jdbcTemplate.update(
				sql,
				user.getEmail(),
				user.getUsername(),
				user.getPassword(),
				user.getRole()) == 1;
	}

	@Override
	public List<User> getAllPosts(int UID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsEmail(String email) {
		String sql = "SELECT email FROM users WHERE email = ? LIMIT 1";
		
		try {	
			jdbcTemplate.queryForObject(sql, String.class, email);
			return true;

		} catch (EmptyResultDataAccessException e) {
			return false;
		}	
	}

	@Override
	public boolean existsUsername(String username) {
		String sql = "SELECT username FROM users WHERE username = ? LIMIT 1";
		
		try {	
			jdbcTemplate.queryForObject(sql, String.class, username);
			return true;

		} catch (EmptyResultDataAccessException e) {
			return false;
		}	

	}

}
