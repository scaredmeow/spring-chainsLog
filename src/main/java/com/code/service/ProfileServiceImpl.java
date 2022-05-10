package com.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.code.dao.UserDao;
import com.code.model.Post;
import com.code.model.User;

@Service
public class ProfileServiceImpl implements ProfileService {

	private UserDao userDao;
	private AuthService authService;
	
	@Autowired
	public ProfileServiceImpl(
			UserDao userDao,
			AuthService authService) {
		this.userDao = userDao;
		this.authService = authService;
	}
	
	@Override
	public ModelAndView displayUserPosts(String username, String viewName) {
		ModelAndView modelAndView = new ModelAndView();
		
		if (this.authService.getUser().equals(username)) {
			User user = this.userDao.findByUserName(username);
			int UID = user.getUser_id();
			List<Post> post = this.userDao.getAllUserPosts(UID);
			modelAndView.addObject("username", username);
			modelAndView.addObject("post", post);
			modelAndView.setViewName(viewName);			
		} else {
			modelAndView.setViewName("redirect:/forum");
		}

		return modelAndView;
	}

}
