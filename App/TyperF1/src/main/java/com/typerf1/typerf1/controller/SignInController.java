package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.model.UserLoginData;
import com.typerf1.typerf1.service.UserLoginDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignInController {

    public final UserLoginDataService userLoginDataService;

    public SignInController(UserLoginDataService userLoginDataService){
        this.userLoginDataService = userLoginDataService;
    }

    @GetMapping("/sign-in")
    public String signin() {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // Here you can perform authentication logic, such as checking the credentials against a database
        // For simplicity, we're just printing the credentials to console
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Redirect to another page after successful login
        return "redirect:/"; // Assuming you have a /home endpoint mapped
    }
}
