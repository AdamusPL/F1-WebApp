package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.service.ParticipantLoginDataService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignInController {

    public final ParticipantLoginDataService participantLoginDataService;

    public SignInController(ParticipantLoginDataService participantLoginDataService){
        this.participantLoginDataService = participantLoginDataService;
    }

    @GetMapping("/sign-in")
    public String signin() {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession httpSession) {
        boolean login = participantLoginDataService.isLoginAndPasswordCorrect(username, password);

        // Redirect to another page after successful login
        if(login) {
            return "redirect:/home";
        }
        else{
            return "redirect:/sign-in";
        }
    }
}
