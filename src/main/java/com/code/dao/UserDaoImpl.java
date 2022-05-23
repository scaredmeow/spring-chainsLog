package com.code.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.code.model.Post;
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
	public User findByUserID(int UID) {
		String sql = "SELECT * FROM users WHERE user_id = " + UID;
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
	public List<Post> getAllUserPosts(int UID) {
		String sql="SELECT p.post_id, p.user_id, u.username, p.title ,p.content, p.vote, p.created_at "
				+ "FROM posts as p JOIN users as u on u.user_id = p.user_id WHERE p.user_id = " 
				+ UID + " ORDER BY created_at DESC" ;
		List<Post> listOfPosts = jdbcTemplate.query(sql, new RowMapper<Post>() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy - hh:mm a");
			
			@Override
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				Post post = new Post();
				String date = dateFormat.format(rs.getTimestamp("created_at"));
				post.setPost_id(rs.getInt("post_id"));
				post.setUser_id(rs.getInt("user_id"));
				post.setUsername(rs.getString("username"));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setVote(rs.getInt("vote"));
				post.setCreated_at(date);
				return post;
			}
		});
		return listOfPosts;
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
