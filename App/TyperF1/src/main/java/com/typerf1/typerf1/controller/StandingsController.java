package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.grandprix.GrandPrixScoreWithJokers;
import com.typerf1.typerf1.dto.standings.GrandPrixDto;
import com.typerf1.typerf1.service.StandingsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StandingsController {

    public final StandingsService standingsService;

    public StandingsController(StandingsService standingsService){
        this.standingsService = standingsService;
    }

    @GetMapping("/get-season-scores")
    public @ResponseBody List<GrandPrixDto> scores(@RequestParam Integer year){
        return standingsService.getScores(year);
    }

    @GetMapping("/get-grandprix-summary")
    public @ResponseBody List<GrandPrixScoreWithJokers> scores(@RequestParam Integer year, @RequestParam String grandPrixName){
        return standingsService.getGrandPrixSummaryScores(year, grandPrixName);
    }

    @GetMapping("/results")
    public String results(){
        return "results";
    }
}
