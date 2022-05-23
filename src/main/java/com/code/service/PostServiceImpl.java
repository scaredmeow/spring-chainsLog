package com.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.code.dao.CommentDao;
import com.code.dao.PostDao;
import com.code.dao.UserDao;
import com.code.model.Comment;
import com.code.model.Post;
import com.code.model.User;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private Post post;
	
	@Autowired
	private Comment comment;
	
	private PostDao postDao;
	private UserDao userDao;
	private CommentDao commentDao;
	private AuthService authService;
	
	@Autowired
	public PostServiceImpl(
			AuthService authService,
			PostDao postDao,
			UserDao userDao,
			CommentDao commentDao) {
		this.authService = authService;
		this.postDao = postDao;
		this.userDao = userDao;
		this.commentDao = commentDao;
	}

	@Override
	public ModelAndView displayPostAndComments(
			int PID, 
			String viewName) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Post post = this.postDao.getPost(PID);
			List<Comment> comment = this.commentDao.getAllComments(PID);
			List<Post> trends = this.postDao.getTrend();
			modelAndView.addObject("trends", trends);
			modelAndView.addObject("username", this.authService.getUser());
			modelAndView.addObject("post", post);
			modelAndView.addObject("comment", comment);
			modelAndView.addObject("commentCount", comment.size());
			modelAndView.addObject("currentUser", this.authService.getUser());
			modelAndView.setViewName(viewName);			
		} catch (Exception e) {
			modelAndView.setViewName("redirect:/forum");
		}
		return modelAndView;	
	}

	@Override
	public String createComment(int PID, String content) {
		User user = this.userDao.findByUserName(this.authService.getUser());
		comment.setUser_id(user.getUser_id());
		comment.setContent(content);
		comment.setPost_id(PID);
		boolean result = this.commentDao.createComment(comment);
		if (!result) {
			return "redirect:/post/"+ PID + "?error";
		} 
		return "redirect:/post/" + PID;
	}

	@Override
	public String deleteComment(int PID, int CID) {
		try {
			if (this.authService.getUser().equals(this.commentDao.getUser(CID))) {
				boolean result = this.commentDao.deleteComment(CID);
				if (!result) {
					return "redirect:/post/"+ PID + "?error";
				} 
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/post/"+PID;
	}

	@Override
	public String deletePost(int PID, String params) {
		try {
			Post post = this.postDao.getPost(PID);
			if (this.authService.getUser().equals(post.getUsername())) {
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

	@Override
	public String updatePostRedirect(int PID, String params, Model model) {
		try {
			Post post = this.postDao.getPost(PID);
			if (this.authService.getUser().equals(post.getUsername())) {
				List<Post> trends = this.postDao.getTrend();
				model.addAttribute("trends", trends);
				model.addAttribute("username", this.authService.getUser());
				model.addAttribute("post", post);
				if(params != null) {
					model.addAttribute("params", "profile");
				}
				model.addAttribute("PID", PID);
				return "update-post";			
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/post/"+PID;
	}

	@Override
	public String updatePost(int PID, String params, String title, String content) {
		post.setContent(content);
		post.setTitle(title);
		post.setPost_id(PID);
		boolean result = this.postDao.updatePost(post);
		if (!result) {
			System.out.println("Error");
		}
		if(params == null) {
			return "redirect:/post/"+ PID;
		}
		return "redirect:/user/" + this.authService.getUser();
	}

	@Override
	public String redirectBackButton(int PID, String params) {
		if(params == null) {
			return "redirect:/post/"+PID;
		}
		return "redirect:/user/" + this.authService.getUser();
	}
	
}
