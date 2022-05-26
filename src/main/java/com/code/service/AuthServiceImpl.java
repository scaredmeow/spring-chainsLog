package com.code.service;

import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.code.MailUtil;
import com.code.dao.UserDao;
import com.code.model.User;
import com.code.security.UserAccount;

@Service
public class AuthServiceImpl implements AuthService {

	String secretKey = System.getenv("SECRET_KEY");
	
	@Autowired
	private User user;
	
	private UserDao userDao;
	private PasswordEncoder passwordEncoder;
	private EncryptionAndDecryptionService authEncryptDecrypt;
	
	public AuthServiceImpl(
			UserDao userDao,
			PasswordEncoder passwordEncoder,
			EncryptionAndDecryptionService authEncryptDecrypt) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
		this.authEncryptDecrypt = authEncryptDecrypt;
	}

	@Override
	public ModelAndView signup(
			String username, 
			String email, 
			String password, 
			String confirmpassword,
			String viewName,
			Model model) {
		
		boolean existsUser = this.userDao.existsUsername(username);
		boolean existsEmail = this.userDao.existsEmail(email);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("existsEmail",existsEmail);
		modelAndView.addObject("existsUser",existsUser);
		
		if (!Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[^\\s]{8,}$",password)) {
			modelAndView.addObject("errorPassword","error");
		}
		if (!confirmpassword.equals(password)) {				
			modelAndView.addObject("errorConfirmPassword","error");
		}
		
		if (!existsEmail && !existsUser) {
			if (confirmpassword.equals(password)) {
				
				user.setEmail(email);
				user.setPassword(passwordEncoder.encode(password));
				user.setUsername(username);
				user.setRole("USER");
				userDao.saveUserRegistration(user);
				
				model.asMap().clear();
				final ModelAndView mav = 
						new ModelAndView("redirect:/signin");
				return mav;
										
			} else {
				modelAndView.setViewName(viewName);
			}
			
		} else {
			modelAndView.setViewName(viewName);
			modelAndView.setStatus(HttpStatus.CONFLICT);
		}

		return modelAndView;
	}

	@Override
	public String getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserAccount) {
			String username = ((UserAccount)principal).getUsername();
			return username;
		} else {
			String username = principal.toString();
			return username;
		}
	}

	@Override
	public String redirect(String viewName) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return viewName;
		}
		
		return "redirect:/forum";
	}

	@Override
	public String resetEmail(String email) {
		boolean existsEmail = this.userDao.existsEmail(email);
		if (existsEmail) {
			Random rand = new Random();
			Integer randSixKey = rand.nextInt(999999);
			String hashedlink = this.authEncryptDecrypt.encrypt(email+randSixKey, secretKey);
			MailUtil.sendMail(email, hashedlink);
			return "redirect:/reset?success";
		}
		return "redirect:/reset?error";
	}

	@Override
	public ModelAndView resetSavePassword(String hashedkey, String password, String confirmpassword) {
		ModelAndView modelAndView = new ModelAndView();
		String hashed = this.authEncryptDecrypt.decrypt(hashedkey, secretKey);
		modelAndView.addObject("email",hashed.substring(0,hashed.length()-6));
		
		if (!Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[^\\s]{8,}$",password)) {
			modelAndView.addObject("errorPassword","error");
			modelAndView.setViewName("reset-password");
		}
		if (!confirmpassword.equals(password)) {				
			modelAndView.addObject("errorConfirmPassword","error");
			modelAndView.setViewName("reset-password");
		} 
		if (Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[^\\s]{8,}$",password) && confirmpassword.equals(password)){
				user.setEmail(hashed.substring(0,hashed.length()-6));
				user.setPassword(passwordEncoder.encode(password));
				boolean result = this.userDao.changeUserPassword(user);
				if (result) {
					modelAndView.setViewName("redirect:/signin");
				}
				modelAndView.setViewName("redirect:/reset");
			}
		return modelAndView;
	}

	@Override
	public String resetRedirect(String hashedkey, Model model) {
		String hashed = this.authEncryptDecrypt.decrypt(hashedkey, secretKey);
		model.addAttribute("email",hashed.substring(0,hashed.length()-6));
		return "reset-password";
	}
	
}
