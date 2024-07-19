package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.GrandPrixScoreWithJokers;
import com.typerf1.typerf1.dto.ScoreWithJokers;
import com.typerf1.typerf1.service.StandingsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StandingsController {

    public final StandingsService standingsRepository;

    public StandingsController(StandingsService standingsRepository){
        this.standingsRepository = standingsRepository;
    }

    @GetMapping("/standings")
    public String standings() {
        return "standings";
    }

    @GetMapping("/get-season-scores")
    public @ResponseBody List<ScoreWithJokers> scores(@RequestParam Integer year){
        return standingsRepository.getScores(year);
    }

    @GetMapping("/get-grandprix-summary")
    public @ResponseBody List<GrandPrixScoreWithJokers> scores(@RequestParam Integer year, @RequestParam String grandPrixName){
        return standingsRepository.getGrandPrixSummaryScores(year, grandPrixName);
    }
}
