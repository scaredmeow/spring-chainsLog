package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.code.service.ForumService;

@Controller
@RequestMapping("/forum")
public class ForumController {
	
	private ForumService forumService;
	
	@Autowired
	public ForumController(ForumService forumService) {
		this.forumService = forumService;
	}	
	
	@GetMapping
	public ModelAndView forumPage() {
		return this.forumService.displayPostandUser("forum/forum");
	}
	
	@GetMapping("/create-a-post")
	public ModelAndView submitPage() {
		return this.forumService.displayPostandUser("forum/createPost");
	}
	
	@PostMapping
	public String forumPost(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			Model model) {
		return this.forumService.createPost(title, content, model);
	}
	
}
