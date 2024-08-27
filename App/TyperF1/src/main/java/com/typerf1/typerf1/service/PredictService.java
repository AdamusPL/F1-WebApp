package com.typerf1.typerf1.service;

import com.typerf1.typerf1.model.GrandPrix;
import com.typerf1.typerf1.model.Predictions;
import com.typerf1.typerf1.model.Session;
import com.typerf1.typerf1.repository.GrandPrixRepository;
import com.typerf1.typerf1.repository.PredictionsRepository;
import com.typerf1.typerf1.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictService {

    private final GrandPrixRepository grandPrixRepository;
    private final SessionRepository sessionRepository;
    private final PredictionsRepository predictionsRepository;

    @Autowired
    public PredictService(GrandPrixRepository grandPrixRepository, SessionRepository sessionRepository, PredictionsRepository predictionsRepository){
        this.grandPrixRepository = grandPrixRepository;
        this.sessionRepository = sessionRepository;
        this.predictionsRepository = predictionsRepository;
    }

    public List<GrandPrix> getThisYearGrandPrix(int year){
        return grandPrixRepository.getThisYearGrandPrixWeekends(year);
    }

    public List<Session> getSessionsOfGrandPrix(int grandPrixId){
        return sessionRepository.getSessionsFromThatGrandPrix(grandPrixId);
    }

    public ResponseEntity<String> postPredictions(){
        Predictions predictions = new Predictions();
        predictionsRepository.save(predictions);
        return ResponseEntity.ok().build();
    }
}
