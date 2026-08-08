package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.predictions.PredictionsDto;
import com.typerf1.typerf1.dto.predictions.PredictionsPostDto;
import com.typerf1.typerf1.model.Driver;
import com.typerf1.typerf1.model.GrandPrix;
import com.typerf1.typerf1.model.Session;
import com.typerf1.typerf1.service.PredictService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
public class PredictController {

    private final PredictService predictService;

    public PredictController(PredictService predictService){
        this.predictService = predictService;
    }

    @GetMapping("/predict")
    public String grandPrix(Model model){
        int year = 2024;
        model.addAttribute("grandPrixWeekends", predictService.getThisYearGrandPrix(year));
        return "predict";
    }

    @GetMapping("/get-grand-prix")
    public @ResponseBody List<GrandPrix> getGrandPrix(){
        int year = 2024;
        return predictService.getThisYearGrandPrix(year);
    }

    @GetMapping("/get-sessions")
    public @ResponseBody List<Session> getSessions(@RequestParam int grandPrixId){
        return predictService.getSessionsOfGrandPrix(grandPrixId);
    }

    @PostMapping("/post-predictions")
    public ResponseEntity<String> postPredictions(@RequestParam int grandPrixId, @RequestParam int sessionId,
                                                  @RequestParam boolean joker,
                                                  @RequestBody PredictionsPostDto predictionsPostDto){
        return predictService.postPredictions(grandPrixId, sessionId, joker, predictionsPostDto);
    }

    @GetMapping("/check-predictions-existence")
    public ResponseEntity<PredictionsDto> checkPredictionsExistence(@RequestParam String sessionType, @RequestParam int year,
                                                                    @RequestParam int grandPrixId, @RequestParam int sessionId
                                                            ) throws ParseException {
        return predictService.checkPredictionsExistence(sessionType, year, grandPrixId, sessionId);
    }

    @GetMapping("/get-current-driver-list")
    public @ResponseBody List<Driver> getCurrentDriverList(){
        return predictService.getCurrentDriverList();
    }
}
