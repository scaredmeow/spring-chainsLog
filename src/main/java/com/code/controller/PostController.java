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

import com.code.service.PostService;

@Controller
@RequestMapping("/post") 
public class PostController {
	
	private PostService postService;

	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;

	}

	@GetMapping("/{post_id}")
	public ModelAndView postPage(@PathVariable("post_id") int PID) {
		return this.postService.displayPostAndComments(PID, "post");
	}

	@PostMapping("/{post_id}")
	public String createCommentPost(
			@PathVariable("post_id") int PID,
			@RequestParam("content") String content) {
		return this.postService.createComment(PID, content);
	}

	@GetMapping("/{post_id}/comment/{comment_id}/delete")
	public String deleteCommentPage(
			@PathVariable("post_id") int PID,
			@PathVariable("comment_id") int CID) {
		return this.postService.deleteComment(PID, CID);
	}	
	
	@GetMapping("/{post_id}/delete")
	public String deletePostPage(
			@PathVariable("post_id") int PID,
			@RequestParam(required = false, value="profile") String params) {
		return this.postService.deletePost(PID, params);
	}

	@GetMapping("/{post_id}/update")
	public String updatePostPage(
			@PathVariable("post_id") int PID,
			@RequestParam(required = false, value="profile") String params,
			Model model) {
		return this.postService.updatePostRedirect(PID, params, model);
	}
	
	@PostMapping("/{post_id}/update")
	public String updatePost(
			@PathVariable("post_id") int PID,
			@RequestParam(required = false, value="profile") String params,
			@RequestParam("title") String title,
			@RequestParam("content") String content) {
		return this.postService.updatePost(PID, params, title, content);
	}
	@PostMapping("/{post_id}/backupdate")
	public String backUpdatePage(
			@PathVariable("post_id") int PID,
			@RequestParam(required = false, value="profile") String params) {
		System.out.println(params);
		return this.postService.redirectBackButton(PID, params);
	}	
}
