package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.service.SeasonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SeasonController {

    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService){
        this.seasonService = seasonService;
    }

    @GetMapping("/results")
    public String results(Model model) {
        model.addAttribute("seasons", seasonService.getSeasons());
        return "results";
    }
}
