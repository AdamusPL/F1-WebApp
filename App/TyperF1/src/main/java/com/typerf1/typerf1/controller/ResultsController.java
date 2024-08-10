package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.SeasonScoreWithJokers;
import com.typerf1.typerf1.service.ResultsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ResultsController {
    public final ResultsService resultsService;

    public ResultsController(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    @GetMapping("/get-participant-standings")
    public @ResponseBody List<SeasonScoreWithJokers> standings(){
        return resultsService.getParticipantStandings(2024);
    }
}
