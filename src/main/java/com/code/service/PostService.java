package com.code.service;

import org.springframework.web.servlet.ModelAndView;

public interface PostService {
	
	public ModelAndView displayPostAndComments(
			int PID,
			String viewName);
}
