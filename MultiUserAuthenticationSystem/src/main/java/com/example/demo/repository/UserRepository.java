package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserDtls;


@Repository
public interface UserRepository  extends JpaRepository<UserDtls,Integer>{

	public boolean existsByEmail(String email);
	
	public UserDtls findByEmail(String email);
    
	public UserDtls findByEmailAndMobileNumber(String email,String Mob_No);
 
	public UserDtls findByVerificationCode(String code);
}
