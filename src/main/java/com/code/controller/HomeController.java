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

@Controller
@RequestMapping(path = "/")
public class HomeController {


	private AuthService authService;
	
	@Autowired
	public HomeController(AuthService authService) {
		this.authService = authService;
	}	
	
	@GetMapping
	public String indexPage(Model model) {
		String username = this.authService.getUser();
		model.addAttribute("username", username);
		return "index";
	}
	
	
	@GetMapping("/signin")
	public String signinPage() {
		return this.authService.redirect("signin");
	}
	

	@GetMapping("/signup")
	public String signupPage() {
		return this.authService.redirect("signup");
	}
	
	@PostMapping("/signup")
	public ModelAndView signupPost(
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("confirmpassword") String confirmpassword,
			Model model) {
		return this.authService.signup(username, email, password, confirmpassword, "signup" ,  model);
	}
	
	@GetMapping("/reset")
	public String resetPage() {
		return this.authService.redirect("reset");
	}
	
	@PostMapping("/reset")
	public String resetPost(@RequestParam("email") String email) {
		return this.authService.resetEmail(email);
	}
	
	@GetMapping("/reset/{hashedkey}")
	public String resetPasswordPage(@PathVariable("hashedkey") String hashedkey, Model model) {
		return this.authService.resetRedirect(hashedkey,model);
	}
	
	@PostMapping("/reset/{hashedkey}")
	public ModelAndView resetSavePost(
			@PathVariable("hashedkey") String hashedkey,
			@RequestParam("password") String password,
			@RequestParam("confirmpassword") String confirmpassword) {
		return this.authService.resetSavePassword(hashedkey, password, confirmpassword);
	}
	
}
