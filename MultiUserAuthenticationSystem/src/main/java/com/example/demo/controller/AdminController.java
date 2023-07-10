package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.UserDtls;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
    public String showAdminPage(Model model) {
        List<UserDtls> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/home";
    }
	
}

