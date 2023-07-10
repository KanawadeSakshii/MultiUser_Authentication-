package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiUserAuthenticationSystemApplication {

	private static final Logger logger=LoggerFactory.getLogger(MultiUserAuthenticationSystemApplication.class);

	public static void main(String[] args) {
		
		logger.info("Application Started ");

		SpringApplication.run(MultiUserAuthenticationSystemApplication.class, args);
	}

}
