package com.code.service;

import org.springframework.web.servlet.ModelAndView;

public interface ProfileService {

	public ModelAndView displayUserPosts(
			String username,
			String viewName);
	
}
