package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.RegisterData;
import com.typerf1.typerf1.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<String> registerUser(@RequestBody RegisterData registerData) {
        return registerService.checkExistence(registerData);
    }
}
