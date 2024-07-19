package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.model.ParticipantLoginData;
import com.typerf1.typerf1.service.ParticipantLoginDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SignInController {

    public final ParticipantLoginDataService participantLoginDataService;

    public SignInController(ParticipantLoginDataService participantLoginDataService) {
        this.participantLoginDataService = participantLoginDataService;
    }

    @GetMapping("/sign-in")
    public String signin() {
        return "sign-in";
    }

    @PostMapping("/check-data")
    public ResponseEntity<String> login(@RequestBody ParticipantLoginData participantLoginData) {
        boolean login = participantLoginDataService.isLoginAndPasswordCorrect(participantLoginData);

        if (login) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
