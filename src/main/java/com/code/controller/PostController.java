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

import com.code.dao.PostDao;
import com.code.dao.UserDao;
import com.code.model.Post;
import com.code.model.User;
import com.code.service.AuthService;
import com.code.service.PostService;

@Controller
@RequestMapping("/post") 
public class PostController {
	
	@Autowired
	private Post post;
	
	private PostDao postDao;
	private UserDao userDao;
	private PostService postService;
	private AuthService authService;
	
	@Autowired
	public PostController(
			PostService postService,
			AuthService authService,
			PostDao postDao,
			UserDao userDao) {
		this.postService = postService;
		this.authService = authService;
		this.postDao = postDao;
		this.userDao = userDao;
	}

	@GetMapping("/{post_id}")
	public ModelAndView postPage(@PathVariable("post_id") int PID) {
		return this.postService.displayPostAndComments(PID, "post/post");
	}

	@GetMapping("/{post_id}/delete")
	public String deletePage(
			@PathVariable("post_id") int PID,
			@RequestParam(required = false, value="profile") String params) {
		try {
			Post post = this.postDao.getPost(PID);
			User user = this.userDao.findByUserID(post.getUser_id());
			if (this.authService.getUser().equals(user.getUsername())) {
				boolean result = this.postDao.deletePost(PID);

				if(params == null) {
					if (!result) {
						return "redirect:/forum?error";
					}
					return "redirect:/forum";
				}
				if (!result) {
					return "redirect:/user/" + this.authService.getUser() + "?error"; 
				}
				return "redirect:/user/" + this.authService.getUser();			
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/post/"+PID;

	}

	@GetMapping("/{post_id}/update")
	public String updatePage(
			@PathVariable("post_id") int PID,
			@RequestParam(required = false, value="profile") String params,
			Model model) {
		try {
			Post post = this.postDao.getPost(PID);
			User user = this.userDao.findByUserID(post.getUser_id());
			if (this.authService.getUser().equals(user.getUsername())) {
				model.addAttribute("post", post);
				if(params != null) {
					model.addAttribute("params", "profile");
				}
				model.addAttribute("PID", PID);
				return "post/updatePost";			
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/post/"+PID;
	}
	
	@PostMapping("/{post_id}/update")
	public String updatePost(
			@PathVariable("post_id") int PID,
			@RequestParam(required = false, value="profile") String params,
			@RequestParam("title") String title,
			@RequestParam("content") String content) {
		post.setContent(content);
		post.setTitle(title);
		post.setPost_id(PID);
		boolean result = this.postDao.updatePost(post);
		if (result) {
			System.out.println("Error");
		}
		if(params == null) {
			return "redirect:/forum";
		}
		return "redirect:/user/" + this.authService.getUser();
	}
	@PostMapping("/{post_id}/backupdate")
	public String backUpdatePage(@RequestParam(required = false, value="profile") String params) {
		if(params == null) {
			return "redirect:/forum";
		}
		return "redirect:/user/" + this.authService.getUser();
	}	
}
