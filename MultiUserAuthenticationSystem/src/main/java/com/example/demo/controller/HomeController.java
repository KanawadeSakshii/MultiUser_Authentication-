package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.UserDtls;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	
	@ModelAttribute 
	private void userDetails(Model m, Principal p) {
		if(p!=null) {
			String email = p.getName();
			UserDtls user= userRepository.findByEmail(email);
			m.addAttribute("user", user);
		}
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	@GetMapping("/signin")
	public String login() {
		return "login";

	}
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/createUser")
	public String createUser(@ModelAttribute UserDtls user, HttpSession session,
			              HttpServletRequest request,@RequestParam("ReEnterPassword") String ReEnterPassword) {
	
		String url=request.getRequestURI().toString();
		url=url.replace(request.getServletPath(),"");	
					
		//System.out.println(user);
		boolean f=userService.checkEmail(user.getEmail());
		
		if(f) {
			session.setAttribute("msgs", "Email Id already Exist...!");
		}
		else {
		      if(user!=null && user.getPassword().equals(ReEnterPassword)) {
						session.setAttribute("msgs", "Registration Sucessfully Done...!!!");
						UserDtls UserDtls=userService.createUser(user,url);
		         }
	              	else {
			            session.setAttribute("msgs", "Something Wrong here...!!!");
		        }
		      
	    }
		return "redirect:/register";
	}
	
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code) {
		if(userService.verifyAccount(code)) {
			return "verify_success";
		}else {
			return "failed";
		}
	}
	
	@GetMapping("/loadForgotPassword")
	public String loadForgotPassword() {
		return "forgot_password";
	}
	
	@GetMapping("/loadResetPassword/{id}")
	public String loadResetPassword(@PathVariable int id, Model m) {
		m.addAttribute("id", id);
		return "reset_password";
	}
	
	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestParam String email,
			                 @RequestParam String mobileNumber,
		              	  HttpSession session) {
		
	UserDtls user = userRepository.findByEmailAndMobileNumber(email, mobileNumber);
	
	if(user!=null) {
		return "redirect:/loadResetPassword/" + user.getId() ;
	}else {
		session.setAttribute("msg1", "Invalid Email & Mobile Number");
		return "forgot_password";
	   }
	}
	
	@PostMapping("/changePassword")
	public String resetPassword(@RequestParam String pword, 
			                   @RequestParam Integer id,
			                   HttpSession session) {
		UserDtls user =userRepository.findById(id).get();
		String encrypPassword = bCryptPasswordEncoder.encode(pword);
		user.setPassword(encrypPassword);
		UserDtls updateUser=userRepository.save(user);
		
		if(updateUser!=null) {
			session.setAttribute("msg1", "Password Changed");
		}
		return "redirect:/loadForgotPassword";
	}
}
