package com.code.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.code.model.Post;

@Repository
public class PostDaoImpl implements PostDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean createPost(Post post) {
		String sql = "INSERT INTO posts(user_id,title,content) VALUES (?, ?, ?)";
		
		 return jdbcTemplate.update(
				 sql, 
				 post.getUser_id(),
				 post.getTitle(),
				 post.getContent()) == 1;
	}

	@Override
	public Post getPost(int PID) {
		String sql = "SELECT p.post_id, p.user_id, u.username, p.title ,p.content, p.vote, p.created_at "
				+ "FROM posts as p JOIN users as u on u.user_id = p.user_id WHERE p.post_id = " + PID;
		return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Post.class));
	}
	
	@Override
	public boolean updatePost(Post post) {
		String sql = "UPDATE posts SET title= ?, content= ? WHERE post_id = ?";
		return jdbcTemplate.update(
				 sql, 
				 post.getTitle(),
				 post.getContent(),
				 post.getPost_id()) == 1;
	}

	@Override
	public boolean deletePost(int PID) {
		String sql = "DELETE FROM posts WHERE post_id = ?";
		Object[] args = new Object[] {PID};
		return jdbcTemplate.update(sql, args) == 1;
	}

	@Override
	public List<Post> getAllPost() {
		String sql="SELECT  k.post_id, k.user_id, u.username, k.title , k.content, k.comments, k.created_at\r\n"
				+ "FROM users as u\r\n"
				+ "JOIN (SELECT p.*, count(c.post_id) as comments\r\n"
				+ "FROM (SELECT post_id FROM comments UNION SELECT post_id FROM posts) as n   \r\n"
				+ "LEFT JOIN comments as c \r\n"
				+ "ON c.post_id = n.post_id\r\n"
				+ "LEFT JOIN posts as p\r\n"
				+ "ON p.post_id = n.post_id\r\n"
				+ "GROUP BY p.post_id\r\n"
				+ "ORDER BY COUNT(c.post_id) DESC, p.created_at DESC) as k \r\n"
				+ "ON u.user_id = k.user_id\r\n"
				+ "ORDER BY k.created_at DESC\r\n";
		List<Post> listOfPosts = jdbcTemplate.query(sql, new RowMapper<Post>() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
			
			@Override
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				Post post = new Post();
				String date = dateFormat.format(rs.getTimestamp("created_at"));
				post.setPost_id(rs.getInt("post_id"));
				post.setUser_id(rs.getInt("user_id"));
				post.setUsername(rs.getString("username"));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setVote(rs.getInt("comments"));
				post.setCreated_at(date);
				return post;
			}
		});
		return listOfPosts;
	}

	@Override
	public List<Post> getTrend() {
		String sql="SELECT  k.post_id, k.user_id, u.username, k.title , k.content, k.comments\r\n"
				+ "FROM users as u\r\n"
				+ "JOIN (SELECT p.post_id, p.user_id, p.title , p.content, count(c.post_id) as comments\r\n"
				+ "FROM (SELECT post_id FROM comments UNION SELECT post_id FROM posts) as n   \r\n"
				+ "LEFT JOIN comments as c \r\n"
				+ "ON c.post_id = n.post_id\r\n"
				+ "LEFT JOIN posts as p\r\n"
				+ "ON p.post_id = n.post_id\r\n"
				+ "GROUP BY p.post_id\r\n"
				+ "ORDER BY COUNT(c.post_id) DESC, p.created_at DESC\r\n"
				+ "LIMIT 5) as k \r\n"
				+ "ON u.user_id = k.user_id\r\n"
				+ "ORDER BY k.comments DESC";
		List<Post> listOfPosts = jdbcTemplate.query(sql, new RowMapper<Post>() {
			
			@Override
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				Post post = new Post();
				post.setPost_id(rs.getInt("post_id"));
				post.setUser_id(rs.getInt("user_id"));
				post.setUsername(rs.getString("username"));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setVote(rs.getInt("comments"));
				return post;
			}
		});
		return listOfPosts;
	}

	@Override
	public List<Post> searchPost(String search) {
		String sql = "SELECT p.post_id, p.user_id, u.username, p.title ,p.content, p.vote, p.created_at FROM "
					+ "posts as p JOIN users as u on u.user_id = p.user_id WHERE p.title LIKE ? "
					+ "ORDER BY created_at DESC";
		List<Post> listOfPosts = jdbcTemplate.query(sql, new RowMapper<Post>() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
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
		} , new Object[] {"%" + search.toLowerCase().trim() + "%"} );
		return listOfPosts;
	}

}
