package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.model.Participant;
import com.typerf1.typerf1.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    private final ParticipantService participantService;

    @Autowired
    public HomeController(ParticipantService participantService){
        this.participantService = participantService;
    }

    @GetMapping("/")
    @ResponseBody
    public List<Participant> home() {
        return participantService.getAllParticipants();
    }

}
