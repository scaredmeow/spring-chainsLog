package com.code.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.code.model.Post;


@Component
public interface PostDao {

	public boolean createPost(int UID);
	public boolean updatePost(int PID);
	public boolean deletePost(int PID);
	public List<Post> getAllPost(int UID);

}
