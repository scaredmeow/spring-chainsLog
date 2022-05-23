package com.code.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.code.model.Comment;

@Repository
public class CommentDaoImpl implements CommentDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	
	@Override
	public boolean createComment(Comment comment) {
		String sql = "INSERT INTO comments(post_id,user_id,content) VALUES (?, ?, ?)";
		
		 return jdbcTemplate.update(
				 sql, 
				 comment.getPost_id(),
				 comment.getUser_id(),
				 comment.getContent()) == 1;
	}

	@Override
	public boolean deleteComment(int CID) {
		String sql = "DELETE FROM comments WHERE comment_id = ?";
		Object[] args = new Object[] {CID};
		return jdbcTemplate.update(sql, args) == 1;
	}

	@Override
	public List<Comment> getAllComments(int PID) {
		String sql="SELECT c.comment_id, c.post_id, c.user_id, u.username, c.content, c.created_at "
				+ "FROM comments as c JOIN users as u on u.user_id = c.user_id WHERE c.post_id = " 
				+ PID + " ORDER BY created_at DESC";
		List<Comment> listOfComments = jdbcTemplate.query(sql, new RowMapper<Comment>() {
			@Override
			public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Comment comment = new Comment();
				comment.setComment_id(rs.getInt("comment_id"));
				comment.setPost_id(rs.getInt("post_id"));
				comment.setUser_id(rs.getInt("user_id"));
				comment.setUsername(rs.getString("username"));
				comment.setContent(rs.getString("content"));
				comment.setCreated_at(rs.getTimestamp("created_at"));
				return comment;
			}
		});
		return listOfComments;
	}

	@Override
	public String getUser(int CID) {
		String sql = "SELECT u.username FROM comments as c JOIN users as u on u.user_id = c.user_id WHERE c.comment_id = " + CID;
		return jdbcTemplate.queryForObject(sql, String.class);


	}


}
