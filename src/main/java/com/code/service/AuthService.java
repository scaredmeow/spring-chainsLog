package com.code.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface AuthService {

	public ModelAndView signup(
			String username,
			String email,
			String password,
			String confirmpassword,
			String viewName,
			Model model);
	public String getUser();
	public String redirect(String viewName);
}
