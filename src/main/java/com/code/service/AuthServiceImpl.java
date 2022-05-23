package com.code.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.code.dao.UserDao;
import com.code.model.User;
import com.code.security.UserAccount;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private User user;
	
	private UserDao userDao;
	private PasswordEncoder passwordEncoder;
	
	public AuthServiceImpl(
			UserDao userDao,
			PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public ModelAndView signup(
			String username, 
			String email, 
			String password, 
			String confirmpassword,
			String viewName,
			Model model) {
		
		boolean existsUser = this.userDao.existsUsername(username);
		boolean existsEmail = this.userDao.existsEmail(email);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("existsEmail",existsEmail);
		modelAndView.addObject("existsUser",existsUser);
		
		if (!Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[^\\s]{8,}$",password)) {
			modelAndView.addObject("errorPassword","error");
		}
		if (!confirmpassword.equals(password)) {				
			modelAndView.addObject("errorConfirmPassword","error");
		}
		
		if (!existsEmail && !existsUser) {
			if (confirmpassword.equals(password)) {
				
				user.setEmail(email);
				user.setPassword(passwordEncoder.encode(password));
				user.setUsername(username);
				user.setRole("USER");
				userDao.saveUserRegistration(user);
				
				model.asMap().clear();
				final ModelAndView mav = 
						new ModelAndView("redirect:/signin");
				return mav;
										
			} else {
				modelAndView.setViewName(viewName);
			}
			
		} else {
			modelAndView.setViewName(viewName);
			modelAndView.setStatus(HttpStatus.CONFLICT);
		}

		return modelAndView;
	}

	@Override
	public String getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserAccount) {
			String username = ((UserAccount)principal).getUsername();
			return username;
		} else {
			String username = principal.toString();
			return username;
		}
	}

	@Override
	public String redirect(String viewName) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return viewName;
		}
		
		return "redirect:/forum";
	}
	
}
