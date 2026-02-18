package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.participant.BetterFullName;
import com.typerf1.typerf1.dto.participant.ParticipantPage;
import com.typerf1.typerf1.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping("/participants")
    public String participants(Model model) {
        model.addAttribute("participants", participantService.getAllParticipants());
        return "participants";
    }

    @GetMapping("/get-participants")
    public @ResponseBody List<BetterFullName> getParticipants() {
        return participantService.getParticipantFullNames();
    }

    @GetMapping("/get-participants-for-subpage")
    public @ResponseBody List<ParticipantPage> getParticipantsForSubpage() {
        return participantService.getParticipantPages();
    }

    @GetMapping("/personal-best")
    public String personalBest(Model model) {
        model.addAttribute("participants", participantService.getAllParticipants());
        return "personal-best"; // Name of your HTML file without .html extension
    }

}
