package com.code.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.code.model.User;
import com.code.service.AuthService;

@RestController
@RequestMapping(path = "api/v1")
public class AuthController {

	private final AuthService authService;
	

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody User user) {
		return ResponseEntity.created(
				URI.create(
						ServletUriComponentsBuilder.
						fromCurrentContextPath().
						path("api/v1/signup").
						toUriString())
		).body(authService.signup(user));
	}

	@PostMapping("/login")
	public ResponseEntity<Boolean> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
				return ResponseEntity.ok().body(authService.login(username, password));
	}
	
}
