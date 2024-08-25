package com.typerf1.typerf1.service;

import com.typerf1.typerf1.model.GrandPrix;
import com.typerf1.typerf1.model.Session;
import com.typerf1.typerf1.repository.GrandPrixRepository;
import com.typerf1.typerf1.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictService {

    private final GrandPrixRepository grandPrixRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public PredictService(GrandPrixRepository grandPrixRepository, SessionRepository sessionRepository){
        this.grandPrixRepository = grandPrixRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<GrandPrix> getThisYearGrandPrix(int year){
        return grandPrixRepository.getThisYearGrandPrixWeekends(year);
    }

    public List<Session> getSessionsOfGrandPrix(int grandPrixId){
        return sessionRepository.getSessionsFromThatGrandPrix(grandPrixId);
    }

}
