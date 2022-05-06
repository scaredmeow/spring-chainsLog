package com.code.dao;

import java.util.List;

import javax.xml.stream.events.Comment;

import org.springframework.stereotype.Component;

@Component
public interface CommentDao {

	public boolean createComment(int PID);
	public boolean updateComment(int CID);
	public boolean deleteComment(int CID);
	public List<Comment> getAllComments(int PID);
	
}
