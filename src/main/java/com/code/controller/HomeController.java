package com.code.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.code.security.UserAccount;

@Controller
@RequestMapping(path = "/")
public class HomeController {

	
	@GetMapping
	public String hello(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserAccount) {
		  int username = ((UserAccount)principal).getUID();
		  model.addAttribute("hello", username);
		} else {
		  String username = principal.toString();
		  model.addAttribute("hello", username);
		}
		
		return "index";
	}
	
	@GetMapping("/signin")
	public String login() {
		return "signin";
	}
	
	@GetMapping("authenticated")
	public String template() {
		return "template";
	}
	
	@GetMapping("admin")
	public String admin() {
		return "admin";
	}
}
