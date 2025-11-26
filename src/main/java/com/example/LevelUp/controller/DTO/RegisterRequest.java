package com.example.LevelUp.controller.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String correo;
    private String password;
    private String role; 
}
