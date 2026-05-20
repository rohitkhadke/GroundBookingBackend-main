package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private Integer id;
    private String name;
    private String email;
    private String role;
    
    private String token;
}