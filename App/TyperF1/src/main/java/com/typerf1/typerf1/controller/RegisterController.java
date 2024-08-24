package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.RegisterData;
import com.typerf1.typerf1.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService){
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register-user")
    public ResponseEntity<String> registerUser(@ModelAttribute RegisterData registerData) throws IOException {
        return registerService.checkExistence(registerData);
    }
}
