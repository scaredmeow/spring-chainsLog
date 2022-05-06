package com.code.model;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class Post {
	private int post_id;
	private int user_id;
	private String title;
	private String content;
	private int vote;
	private Timestamp created_at;
	
	public Post() {
	}

	public Post(int post_id, 
			int user_id, 
			String title, 
			String content, 
			int vote, 
			Timestamp created_at) {
		this.post_id = post_id;
		this.user_id = user_id;
		this.title = title;
		this.content = content;
		this.vote = vote;
		this.created_at = created_at;
	}

	public Post(int user_id, 
			String title, 
			String content, 
			int vote, 
			Timestamp created_at) {
		this.user_id = user_id;
		this.title = title;
		this.content = content;
		this.vote = vote;
		this.created_at = created_at;
	}

	public Post(int user_id, 
			String title, 
			String content, 
			int vote) {
		this.user_id = user_id;
		this.title = title;
		this.content = content;
		this.vote = vote;
	}

	public Post(int user_id, 
			String title, 
			String content) {
		this.user_id = user_id;
		this.title = title;
		this.content = content;
	}

	public Post(int user_id, 
			String title, 
			String content, 
			Timestamp created_at) {
		this.user_id = user_id;
		this.title = title;
		this.content = content;
		this.created_at = created_at;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Post {post_id=" + post_id + 
				", user_id=" + user_id + 
				", title='" + title + "\'" +
				", content='" + content + "\'" +
				", vote=" + vote + 
				", created_at=" + created_at + "}";
	}
	
	
}
