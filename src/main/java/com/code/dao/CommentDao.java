package com.code.dao;

import java.util.List;


import org.springframework.stereotype.Component;

import com.code.model.Comment;

@Component
public interface CommentDao {
	
	public String getUser(int CID);
	public boolean createComment(Comment comment);
	public boolean deleteComment(int CID);
	public List<Comment> getAllComments(int PID);
	
}
