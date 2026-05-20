package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginRequestDTO;
import com.dto.LoginResponseDTO;
import com.dto.RegisterRequestDTO;
import com.entity.User;
import com.service.UserService;

import jakarta.validation.Valid;




@RestController
@CrossOrigin
public class UserController {

	@Autowired
	UserService userv;
	//Add User
	@PostMapping("/reguser")
	public String reguser(
	    @Valid @RequestBody RegisterRequestDTO dto
	) {
	    return userv.registeruser(dto);
	}

	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO u) {

	    return userv.login(
	        u.getEmail(),
	        u.getPassword()
	    );
	}
	
	@GetMapping("/allusers")
	public List<User> getAllUsers() {
	    return userv.getAllUsers();
	}
	
	@GetMapping("/search")
	public List<User> searchUsers(@RequestParam String keyword) {
	    return userv.searchUsers(keyword);
	}

}
