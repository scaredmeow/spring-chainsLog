package com.code.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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
		String sql = "SELECT * FROM posts WHERE post_id = " + PID;
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
		String sql="SELECT * FROM posts ORDER BY post_id DESC";
		List<Post> listOfPosts = jdbcTemplate.query(sql, new RowMapper<Post>() {
			@Override
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				Post post = new Post();
				post.setPost_id(rs.getInt("post_id"));
				post.setUser_id(rs.getInt("user_id"));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setVote(rs.getInt("vote"));
				post.setCreated_at(rs.getTimestamp("created_at"));
				return post;
			}
		});
		return listOfPosts;
	}

}
