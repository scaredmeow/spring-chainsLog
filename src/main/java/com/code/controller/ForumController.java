package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.code.service.AuthService;
import com.code.service.ForumService;

@Controller
@RequestMapping("/forum")
public class ForumController {
	
	private AuthService authService;
	private ForumService forumService;
	
	@Autowired
	public ForumController(
			AuthService authService,
			ForumService forumService) {
		this.authService = authService;
		this.forumService = forumService;
	}	
	
	@GetMapping()
	public String forumPage() {
		return "redirect:/forum/" + this.authService.getUser();
	}
	
	@GetMapping("/{username}")
	public ModelAndView forumPageUser(@PathVariable("username") String username) {
		return this.forumService.getPathUsername(username, "forum");
	}
	
	@GetMapping("/{username}/submit")
	public ModelAndView submitPage(@PathVariable("username") String username) {
		return this.forumService.getPathUsername(username, "submit");
	}
	
	@PostMapping("/{username}") 
	public String forumPost(
			@PathVariable("username") String username,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			Model model) {
		return this.forumService.createPost(username, title, content, model);
	}
	
}
