package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.UserDtls;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@ModelAttribute 
	private void userDetails(Model m, Principal p) {
		String email = p.getName();
		UserDtls user= userRepository.findByEmail(email);
		m.addAttribute("user", user);
	}
 	
	@GetMapping("/")
	public String Home() {
		return "user/home";
	}
	
	@GetMapping("/changepassword")
	public String loadChangePassword() {
		return "user/change_password";
	}
	
	@PostMapping("/updatePassword")
	public String changePassword(Principal p, 
			@RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass,
			  HttpSession session) {
		
		String email=p.getName();
		UserDtls loginUser=userRepository.findByEmail(email);
		boolean t=bCryptPasswordEncoder.matches(oldPass, loginUser.getPassword());
		
		if(t){
			
			loginUser.setPassword(bCryptPasswordEncoder.encode(newPass));
			UserDtls updatedPasswordUser=userRepository.save(loginUser);
			
			if(updatedPasswordUser!=null) {
				session.setAttribute("msg", "Password Changed Sucessfully");
		    }else {
				session.setAttribute("msg", "Something Wrong on Server");
			       }
		}
		else{
			session.setAttribute("msg", "Old Password Incorrect....!");
		}
		return "redirect:/user/changepassword";
	}
}
