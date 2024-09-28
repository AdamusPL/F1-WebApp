package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.model.Predictions;
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

    @GetMapping("/get-sessions")
    public @ResponseBody List<Session> getSessions(@RequestParam int grandPrixId){
        return predictService.getSessionsOfGrandPrix(grandPrixId);
    }

    @PostMapping("/post-predictions")
    public ResponseEntity<String> postPredictions(@RequestParam int grandPrixId, @RequestParam int sessionId,
                                                  @RequestParam String username,
                                                  @ModelAttribute Predictions predictions){
        return predictService.postPredictions(grandPrixId, sessionId, username, predictions);
    }

    @GetMapping("/check-predictions-existence")
    public ResponseEntity<Predictions> checkPredictionsExistence(@RequestParam String sessionType, @RequestParam int year,
                                                                 @RequestParam int grandPrixId, @RequestParam int sessionId,
                                                            @RequestParam String username) throws ParseException {
        return predictService.checkPredictionsExistence(sessionType, year, grandPrixId, sessionId, username);
    }

    @GetMapping("/calculate-points-qualifying")
    public ResponseEntity<String> calculatePointsQualifying(@RequestParam int grandPrixId, @RequestParam int sessionId,
                                              @RequestParam String username) throws ParseException {
        int year = 2024;
        return predictService.F1APIQualifyingParser(grandPrixId, sessionId, username, year);
    }

    @GetMapping("/calculate-points-race")
    public ResponseEntity<String> calculatePointsRace(@RequestParam int grandPrixId, @RequestParam int sessionId,
                                        @RequestParam String username) throws ParseException {
        int year = 2024;
        return predictService.F1APIRaceParser(grandPrixId, sessionId, username, year);
    }
}
