package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.config.JwtUtil;
import com.dto.LoginResponseDTO;
import com.dto.RegisterRequestDTO;
import com.entity.User;
import com.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    UserRepo urep;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    JwtUtil jwtUtil;

    // ================= REGISTER =================
    public String registeruser(RegisterRequestDTO dto) {

        if (urep.existsByEmail(dto.getEmail())) {
            return "Email already exists...";
        }

        User u = new User();

        u.setName(dto.getName());

        u.setEmail(dto.getEmail());

        // PASSWORD ENCRYPTION
        u.setPassword(
            passwordEncoder.encode(dto.getPassword())
        );

        u.setPhoneno(dto.getPhoneno());

        u.setAddress(dto.getAddress());

        u.setRole("user");

        urep.save(u);

        return "User Registered";
    }

    // ================= LOGIN =================
    public LoginResponseDTO login(
            String email,
            String password
    ) {

        User user = urep.findByEmail(email);

        if (
            user != null &&
            passwordEncoder.matches(
                password,
                user.getPassword()
            )
        ) {

            LoginResponseDTO dto =
                new LoginResponseDTO();

            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());

            String token =
            	    jwtUtil.generateToken(
            	        user.getEmail()
            	    );

            	dto.setToken(token);
            	
            return dto;
        }

        return null;
    }

    // ================= GET USERS =================
    public List<User> getAllUsers() {
        return urep.findAll();
    }

    // ================= SEARCH USERS =================
    public List<User> searchUsers(String keyword) {

        return urep
            .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword,
                keyword
            );
    }
}