package com.code.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface ForumService {
	
	public ModelAndView displayPostandUser(String viewName);
	public String createPost(
			String title,
			String content,
			Model model);
}
