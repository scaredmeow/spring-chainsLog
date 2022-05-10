package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.code.service.AuthService;
import com.code.service.ProfileService;

@Controller
@RequestMapping(path = "/user")
public class ProfileController {
	
	private AuthService authService;
	private ProfileService profileService;
	
	@Autowired
	public ProfileController(
			AuthService authService,
			ProfileService profileService) {
		this.authService = authService;
		this.profileService = profileService;
	}
	
	@GetMapping
	public String userRedirect() {
		return "redirect:/user/" + this.authService.getUser();
	}
	
	@GetMapping("/{username}")
	public ModelAndView userPage(@PathVariable("username") String username) {
		return this.profileService.displayUserPosts(username, "profile/profile");
	}

}
