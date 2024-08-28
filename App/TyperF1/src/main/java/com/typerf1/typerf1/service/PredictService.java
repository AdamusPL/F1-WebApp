package com.typerf1.typerf1.service;

import com.typerf1.typerf1.model.*;
import com.typerf1.typerf1.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictService {

    private final GrandPrixRepository grandPrixRepository;
    private final SessionRepository sessionRepository;
    private final PredictionsRepository predictionsRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public PredictService(GrandPrixRepository grandPrixRepository, SessionRepository sessionRepository,
                          PredictionsRepository predictionsRepository, ParticipantRepository participantRepository){
        this.grandPrixRepository = grandPrixRepository;
        this.sessionRepository = sessionRepository;
        this.predictionsRepository = predictionsRepository;
        this.participantRepository = participantRepository;
    }

    public List<GrandPrix> getThisYearGrandPrix(int year){
        return grandPrixRepository.getThisYearGrandPrixWeekends(year);
    }

    public List<Session> getSessionsOfGrandPrix(int grandPrixId){
        return sessionRepository.getSessionsFromThatGrandPrix(grandPrixId);
    }

    public ResponseEntity<String> postPredictions(int grandPrixId, int sessionId, String username, Predictions predictions){
        GrandPrix grandPrix = grandPrixRepository.findById(grandPrixId)
                .orElseThrow(() -> new EntityNotFoundException("GrandPrix not found with id: " + grandPrixId));
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id: " + sessionId));
        Participant participant = participantRepository.getParticipantByParticipantLoginDataUsername(username).get(0);
        predictions.setGrandPrix(grandPrix);
        predictions.setSession(session);
        predictions.setParticipant(participant);

        predictionsRepository.save(predictions);
        return ResponseEntity.ok().build();
    }
}
