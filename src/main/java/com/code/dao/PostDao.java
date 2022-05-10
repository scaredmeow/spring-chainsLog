package com.code.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.code.model.Post;


@Component
public interface PostDao {

	public boolean createPost(Post post);
	public Post getPost(int PID);
	public boolean updatePost(Post post);
	public boolean deletePost(int PID);
	public List<Post> getAllPost();

}
