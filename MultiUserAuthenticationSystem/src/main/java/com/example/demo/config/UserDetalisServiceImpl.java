package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserDtls;
import com.example.demo.repository.UserRepository;


@Service
public class UserDetalisServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		UserDtls user=userRepository.findByEmail(email);
	
		if(user!=null) {
		return new CustomUserDetails(user);
	   }
	throw new UsernameNotFoundException("Your Details are not found ....!");
	
	}

}
