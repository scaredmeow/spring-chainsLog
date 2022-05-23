package com.code.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface PostService {
	
	public ModelAndView displayPostAndComments(
			int PID,
			String viewName);
	public String createComment(int PID, String content);
	public String deleteComment(int PID, int CID);
	public String deletePost(int PID, String params);
	public String updatePostRedirect(int PID, String params, Model model);
	public String updatePost(int PID, String params, String title, String content);
	public String redirectBackButton(int PID, String params);
	
}
