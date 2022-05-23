package com.code.model;


import org.springframework.stereotype.Component;

@Component
public class Comment {
	private int comment_id;
	private int post_id;
	private int user_id;
	private String content;
	private String created_at;
	private String username;

	public Comment() {
	}

	public Comment(int comment_id, 
			int post_id, 
			int user_id, 
			String content, 
			String created_at) {
		this.comment_id = comment_id;
		this.post_id = post_id;
		this.user_id = user_id;
		this.content = content;
		this.created_at = created_at;
	}

	public Comment(int post_id, 
			int user_id, 
			String content, 
			String created_at) {
		this.post_id = post_id;
		this.user_id = user_id;
		this.content = content;
		this.created_at = created_at;
	}

	public Comment(int post_id, 
			int user_id, 
			String content) {
		this.post_id = post_id;
		this.user_id = user_id;
		this.content = content;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "Comment {comment_id=" + comment_id + 
				", post_id=" + post_id + 
				", user_id=" + user_id + 
				", username='" + username + "\'" +
				", content='" + content + "\'" +
				", created_at=" + created_at + "}";
	}


	
	
	
}
