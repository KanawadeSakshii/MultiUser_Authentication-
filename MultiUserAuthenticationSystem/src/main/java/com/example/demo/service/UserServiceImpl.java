package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserDtls;
import com.example.demo.repository.UserRepository;

import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public UserDtls createUser(UserDtls userDtls,String url) {
		userDtls.setPassword(passwordEncoder.encode(userDtls.getPassword()));
		userDtls.setRole("ROLE_USER");
		
		userDtls.setEnable(false);
		RandomString rs=new RandomString();
		userDtls.setVerificationCode(rs.make(64));
		
		UserDtls us = userRepository.save(userDtls);
		
		sendVerificationMail(userDtls,url);
		return us;
	}

	@Override
	public boolean checkEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public void sendVerificationMail(UserDtls user, String url) {
		
		String from="kanawadesakshi9970@gmail.com";
		String to=user.getEmail();
		String subject="Account Varification";
		String content="Dear [[name]],<br>"
				+"Please click the link below to verify your form : <br>"
				+"<h3><a href=\"[[url]]\" target=\"_self\">VERIFY</a></h3>"
                 +"Thank You....!,<br>"
				+"Sakshi Kanawade.";
	
		try {
			
			MimeMessage message= mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
			helper.setFrom(from,"sk");
			helper.setTo(to);
			helper.setSubject(subject);
			
			content= content.replace("[[name]]", user.getFullName());
			String siteUrl="http://localhost:8085"+url+"/verify?code="+user.getVerificationCode();
			content=content.replace("[[url]]",siteUrl);
				
			helper.setText(content,true);
			mailSender.send(message);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean verifyAccount(String code) {
		UserDtls user=userRepository.findByVerificationCode(code);
		if(user!=null) {
			user.setEnable(true);
			user.setVerificationCode(null);
			userRepository.save(user);
			return true;

		}
			return false;

		}
		
	}

