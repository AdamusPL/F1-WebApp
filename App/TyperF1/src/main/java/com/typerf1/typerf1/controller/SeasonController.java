package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.model.Season;
import com.typerf1.typerf1.service.SeasonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SeasonController {

    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService){
        this.seasonService = seasonService;
    }

    @GetMapping("/get-season-years")
    public @ResponseBody List<Season> getSeasonYears() {
        return seasonService.getSeasons();
    }

    @GetMapping("/standings")
    public String standings(Model model) {
        model.addAttribute("seasons", seasonService.getSeasons());
        return "standings";
    }
}
