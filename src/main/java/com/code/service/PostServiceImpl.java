package com.code.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.code.dao.PostDao;
import com.code.dao.UserDao;
import com.code.model.Post;
import com.code.model.User;

@Service
public class PostServiceImpl implements PostService {
	
	private UserDao userDao;
	private PostDao postDao;
	private AuthService authService;
	
	@Autowired
	public PostServiceImpl(
			UserDao userDao,
			PostDao postDao,
			AuthService authService) {
		this.postDao = postDao;
		this.authService = authService;
		this.userDao = userDao;
	}

	@Override
	public ModelAndView displayPostAndComments(
			int PID, 
			String viewName) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Post post = this.postDao.getPost(PID);
			User user = this.userDao.findByUserName(this.authService.getUser());
			modelAndView.addObject("username", this.authService.getUser());
			modelAndView.addObject("post", post);
			modelAndView.addObject("current_UID", user.getUser_id());
			modelAndView.setViewName(viewName);			
		} catch (Exception e) {
			modelAndView.setViewName("redirect:/forum");
		}
		return modelAndView;	
	}

}
