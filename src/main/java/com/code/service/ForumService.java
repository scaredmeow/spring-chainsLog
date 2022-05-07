package com.code.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface ForumService {
	
	public ModelAndView getPathUsername(
			String username,
			String viewName);
	public String createPost(
			String username,
			String title,
			String content,
			Model model);
}
