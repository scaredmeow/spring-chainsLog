package com.code.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.code.service.AuthService;

@Controller
public class CustomErrorController implements ErrorController {

	private AuthService authService;
	
	@Autowired
	public CustomErrorController(AuthService authService) {
		this.authService = authService;
	}


    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
		String username = this.authService.getUser();
		model.addAttribute("username", username);
        String errorPage = "error/error"; // default
         
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
         
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
             
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle HTTP 404 Not Found error
                errorPage = "error/404";
                 
//            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
//                // handle HTTP 403 Forbidden error
//                errorPage = "error/403";
//                 
//            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                // handle HTTP 500 Internal Server error
//                errorPage = "error/500";
//                 
            }
        }
         
        return errorPage;
    }
     
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }	
}
