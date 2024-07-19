package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.GrandPrixScoreWithJokers;
import com.typerf1.typerf1.dto.ScoreWithJokers;
import com.typerf1.typerf1.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StandingsController {

    public final StatisticsService statisticsService;

    public StandingsController(StatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }

    @GetMapping("/standings")
    public String standings() {
        return "standings";
    }

    @GetMapping("/get-season-scores")
    public @ResponseBody List<ScoreWithJokers> scores(@RequestParam Integer year){
        return statisticsService.getScores(year);
    }

    @GetMapping("/get-grandprix-summary")
    public @ResponseBody List<GrandPrixScoreWithJokers> scores(@RequestParam Integer year, @RequestParam String grandPrixName){
        return statisticsService.getGrandPrixSummaryScores(year, grandPrixName);
    }
}
