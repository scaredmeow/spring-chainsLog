package com.code.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.code.dao.UserDao;
import com.code.model.User;

@Service
public class UserAccountService implements UserDetailsService {
	
	private UserDao userDao;
	
	public UserAccountService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userDao.findByUserName(username);
		UserAccount userAccount = new UserAccount(user);
		return userAccount;
	}

}
