package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	public AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Bean
	public UserDetailsService getuserDetailsServices() {
		return new UserDetalisServiceImpl();
	};
	
	@Bean
	public BCryptPasswordEncoder getpasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider getDaoAuthProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getuserDetailsServices()); 
		daoAuthenticationProvider.setPasswordEncoder(getpasswordEncoder());
		return daoAuthenticationProvider;
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
             http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN")
             .requestMatchers("/user/**").hasRole("USER")
             .requestMatchers("/**").permitAll()
             .and()
             .formLogin()
             .loginPage("/signin")
             .loginProcessingUrl("/login")
             .successHandler(authenticationSuccessHandler)
             .and().csrf().disable();
             http.authenticationProvider(getDaoAuthProvider());
             return http.build();
             
	}
}
