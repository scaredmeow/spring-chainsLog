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
	private AuthService authService;	
	
	public ForumServiceImpl(
			UserDao userDao,
			PostDao postDao,
			AuthService authService) {
		this.userDao = userDao;
		this.postDao = postDao;
		this.authService = authService;
	}

	@Override
	public ModelAndView displayPostandUser(String viewName) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("username", this.authService.getUser());
		List<Post> post = this.postDao.getAllPost();
		modelAndView.addObject("post", post);
		List<Post> trends = this.postDao.getTrend();
		modelAndView.addObject("trends", trends);
		modelAndView.setViewName(viewName);
		return modelAndView;
	}

	@Override
	public String createPost(
			String title, 
			String content, 
			Model model) {
		List<Post> trends = this.postDao.getTrend();
		model.addAttribute("trends", trends);
		user = this.userDao.findByUserName(this.authService.getUser());
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

	@Override
	public ModelAndView search(String search, String viewName) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("username", this.authService.getUser());
		List<Post> post= this.postDao.searchPost(search);
		modelAndView.addObject("post", post);
		List<Post> trends = this.postDao.getTrend();
		modelAndView.addObject("trends", trends);
		modelAndView.setViewName(viewName);
		
		return modelAndView;
	}

}
