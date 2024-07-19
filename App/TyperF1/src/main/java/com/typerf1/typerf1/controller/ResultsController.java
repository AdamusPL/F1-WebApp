package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.SeasonScoreWithJokers;
import com.typerf1.typerf1.service.SeasonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ResultsController {
    public final SeasonService seasonService;

    public ResultsController(SeasonService seasonService){
        this.seasonService = seasonService;
    }

    @GetMapping("/results")
    public String results() {
        return "results";
    }

    @GetMapping("/get-participant-standings")
    public @ResponseBody List<SeasonScoreWithJokers> standings(){
        return seasonService.getParticipantStandings(2024);
    }
}
