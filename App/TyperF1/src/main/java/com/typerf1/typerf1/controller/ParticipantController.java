package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService){
        this.participantService = participantService;
    }

    @GetMapping("/participants")
    public String participants(Model model) {
        model.addAttribute("participants", participantService.getAllParticipants());
        return "participants";
    }

    @GetMapping("/personal-best")
    public String personalBest(Model model) {
        model.addAttribute("participants", participantService.getAllParticipants());
        return "personal-best"; // Name of your HTML file without .html extension
    }

}
