package com.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.code.dao.PostDao;
import com.code.dao.UserDao;
import com.code.model.Post;
import com.code.model.User;

@Service
public class ForumServiceImpl implements ForumService {
	
	@Autowired
	private User user;
	
	@Autowired
	private Post post;
	
	private UserDao userDao;
	private PostDao	postDao;
	
	
	public ForumServiceImpl(UserDao userDao, PostDao postDao) {
		this.userDao = userDao;
		this.postDao = postDao;
	}

	@Override
	public ModelAndView getPathUsername(String username, String viewName) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("username", username);
		List<Post> post = this.postDao.getAllPost();
		modelAndView.addObject("post", post);
		modelAndView.setViewName(viewName);
		return modelAndView;
	}

	@Override
	public String createPost(
			String username, 
			String title, 
			String content, 
			Model model) {
		user = this.userDao.findByUserName(username);
		int UID = user.getUser_id();
		post.setContent(content);
		post.setUser_id(UID);
		post.setTitle(title);
		boolean update = this.postDao.createPost(post);
		if (!update) {
			return "redirect:/forum?error";
		}
		return "redirect:/forum";
	}

}
