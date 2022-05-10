package com.code.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private UserAccountService userAccountService;
	
	public SecurityConfiguration(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(
					"/",
					"/signup", 
					"/forum").permitAll()
			.antMatchers(
					"/forum/**",
					"/post/**",
					"/user/**",
					"/post",
					"/post/**").authenticated()
			.and()
			.formLogin()
				.loginPage("/signin").permitAll()
				.defaultSuccessUrl("/forum")
			.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
				.logoutSuccessUrl("/signin")
			.and()
				.rememberMe()
				.tokenValiditySeconds(2592000).key("mySecret");
	}
	

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userAccountService);
		return daoAuthenticationProvider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
	
}
