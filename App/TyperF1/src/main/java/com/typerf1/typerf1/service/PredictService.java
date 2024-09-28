package com.typerf1.typerf1.service;

import com.typerf1.typerf1.model.*;
import com.typerf1.typerf1.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import com.typerf1.typerf1.model.Predictions;

import com.typerf1.typerf1.tools.PointsCalculator;

@Service
public class PredictService {

    private final GrandPrixRepository grandPrixRepository;
    private final SessionRepository sessionRepository;
    private final PredictionsRepository predictionsRepository;
    private final ParticipantRepository participantRepository;
    private final PointsRepository pointsRepository;

    @Autowired
    public PredictService(GrandPrixRepository grandPrixRepository, SessionRepository sessionRepository,
                          PredictionsRepository predictionsRepository, ParticipantRepository participantRepository,
                          PointsRepository pointsRepository) {
        this.grandPrixRepository = grandPrixRepository;
        this.sessionRepository = sessionRepository;
        this.predictionsRepository = predictionsRepository;
        this.participantRepository = participantRepository;
        this.pointsRepository = pointsRepository;
    }

    public List<GrandPrix> getThisYearGrandPrix(int year) {
        return grandPrixRepository.getThisYearGrandPrixWeekends(year);
    }

    public List<Session> getSessionsOfGrandPrix(int grandPrixId) {
        return sessionRepository.getSessionsFromThatGrandPrix(grandPrixId);
    }

    public ResponseEntity<String> postPredictions(int grandPrixId, int sessionId, String username, Predictions predictions) {
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

    public ResponseEntity<Predictions> checkPredictionsExistence(int grandPrixId, int sessionId, String username) {
        List<Predictions> predictionsList = predictionsRepository.checkPredictionExistence(grandPrixId, sessionId, username);
        if (predictionsList.size() != 0) {
            Predictions predictions = predictionsList.get(0);
            predictions.setParticipant(null);
            predictions.setSession(null);
            predictions.setGrandPrix(null);
            return ResponseEntity.ok(predictions);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    public Predictions getParticipantPredictions(int grandPrixId, int sessionId, String username) {
        List<Predictions> predictionsList = predictionsRepository.checkPredictionExistence(grandPrixId, sessionId, username);
        return predictionsList.get(0);
    }

    public ResponseEntity<String> F1APIQualifyingParser(int grandPrixId, int sessionId, String username, int year) {
        boolean joker = false;
        Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);

        double pointsCalculated;

        //if points were already calculated
        if (predictions.getPoints() != null) {
            return ResponseEntity.ok(predictions.getPoints().getNumber().toString());
        }

        //page with api with F1 race results
        String url = "https://ergast.com/api/f1/" + year + "/" + grandPrixId + "/qualifying";
        LinkedHashMap<?, ?> raceMap = getTreeToRaces(url);

        if (raceMap == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Step 3: Access the "ResultsList" LinkedHashMap
        LinkedHashMap<?, ?> resultsListMap = (LinkedHashMap<?, ?>) raceMap.get("QualifyingList");

        // Step 4: Access the "Result" LinkedHashMap
        ArrayList<?> resultsArray = (ArrayList<?>) resultsListMap.get("QualifyingResult");

        List<String> driverStandings = parseSurnames(resultsArray);

        PointsCalculator pointsCalculator = initPointsCalculator(predictions, driverStandings, joker);

        pointsCalculated = pointsCalculator.countPointsFromQualifying();

        Points points = new Points(pointsCalculated);
        points.setParticipant(predictions.getParticipant());
        points.setSession(predictions.getSession());
        Predictions predictions1 = predictionsRepository.checkPredictionExistence(grandPrixId, sessionId, username).get(0);
        points.setPredictions(predictions1);

        predictions1.setPoints(points);
        pointsRepository.save(points);

        return ResponseEntity.ok(String.valueOf(pointsCalculated));
    }

    private LinkedHashMap<?, ?> getTreeToRaces(String url) {
        RestTemplate restTemplate = new RestTemplate();

        Object[] results = restTemplate.getForObject(url, Object[].class);
        // Step 1: Access the first element of the array, cast it to LinkedHashMap
        LinkedHashMap<?, ?> rootMap = (LinkedHashMap<?, ?>) results[1];

        if (rootMap.isEmpty()) {
            return null;
        }

        // Step 2: Access the "Race" LinkedHashMap
        LinkedHashMap<?, ?> raceMap = (LinkedHashMap<?, ?>) rootMap.get("Race");

        return raceMap;
    }

    private List<String> parseSurnames(ArrayList<?> resultsArray) {
        List<String> driverStandings = new ArrayList<>();
        // Step 5: Iterate through the ArrayList
        for (Object resultObject : resultsArray) {
            // Each element in the ArrayList is a LinkedHashMap
            LinkedHashMap<?, ?> resultMap = (LinkedHashMap<?, ?>) resultObject;

            LinkedHashMap<?, ?> resultDriver = (LinkedHashMap<?, ?>) resultMap.get("Driver");

            String resultFamilyName = (String) resultDriver.get("FamilyName");
            //to match letters like é or ü in Pérez or Hülkenberg with participant predictions
            String normalized = Normalizer.normalize(resultFamilyName, Normalizer.Form.NFD);
            String result = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

            driverStandings.add(result);
        }
        return driverStandings;
    }

    private PointsCalculator initPointsCalculator(Predictions predictions, List<String> driverStandings, boolean joker) {
        List<String> participantPredictions = new ArrayList<>();

        //parse predictions object to arraylist with just driver surnames
        participantPredictions.add(predictions.getDriver1());
        participantPredictions.add(predictions.getDriver2());
        participantPredictions.add(predictions.getDriver3());
        participantPredictions.add(predictions.getDriver4());
        participantPredictions.add(predictions.getDriver5());
        participantPredictions.add(predictions.getDriver6());
        participantPredictions.add(predictions.getDriver7());
        participantPredictions.add(predictions.getDriver8());
        participantPredictions.add(predictions.getDriver9());
        participantPredictions.add(predictions.getDriver10());
        participantPredictions.add(predictions.getDriver11());
        participantPredictions.add(predictions.getDriver12());
        participantPredictions.add(predictions.getDriver13());
        participantPredictions.add(predictions.getDriver14());
        participantPredictions.add(predictions.getDriver15());
        participantPredictions.add(predictions.getDriver16());
        participantPredictions.add(predictions.getDriver17());
        participantPredictions.add(predictions.getDriver18());
        participantPredictions.add(predictions.getDriver19());
        participantPredictions.add(predictions.getDriver20());

        return new PointsCalculator(driverStandings, participantPredictions, joker);
    }

    public ResponseEntity<String> F1APIRaceParser(int grandPrixId, int sessionId, String username, int year) {
        boolean joker = false;
        Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);

        double pointsCalculated;

        //if points were already calculated
        if (predictions.getPoints() != null) {
            return ResponseEntity.ok(predictions.getPoints().getNumber().toString());
        }

        //page with api with F1 race results (standings)
        String url = "https://ergast.com/api/f1/" + year + "/" + grandPrixId + "/results";
        LinkedHashMap<?, ?> raceMap = getTreeToRaces(url);

        if (raceMap == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Step 3: Access the "ResultsList" LinkedHashMap
        LinkedHashMap<?, ?> resultsListMap = (LinkedHashMap<?, ?>) raceMap.get("ResultsList");

        // Step 4: Access the "Result" LinkedHashMap
        ArrayList<?> resultsArray = (ArrayList<?>) resultsListMap.get("Result");

        List<String> driverStandings = parseSurnames(resultsArray);

        PointsCalculator pointsCalculator = initPointsCalculator(predictions, driverStandings, joker);

        //page with the fastest laps in race
        String urlFastestLap = "https://ergast.com/api/f1/" + year + "/" + grandPrixId + "/fastest/" + 1 + "/results";

        LinkedHashMap<?, ?> raceMapFL = getTreeToRaces(urlFastestLap);

        // Step 3: Access the "ResultsList" LinkedHashMap
        LinkedHashMap<?, ?> resultsListMapFL = (LinkedHashMap<?, ?>) raceMapFL.get("ResultsList");

        LinkedHashMap<?, ?> result = (LinkedHashMap<?, ?>) resultsListMapFL.get("Result");

        LinkedHashMap<?, ?> resultDriver = (LinkedHashMap<?, ?>) result.get("Driver");

        String actualFastestLap = (String) resultDriver.get("FamilyName");

        pointsCalculated = pointsCalculator.countPointsFromRace(predictions.getFastestLap(), actualFastestLap);

        Participant participant = participantRepository.getParticipantByParticipantLoginDataUsername(username).get(0);
        Optional<Session> session = sessionRepository.findById(sessionId);
        Optional<GrandPrix> grandPrix = grandPrixRepository.findById(grandPrixId);

        Points points = new Points(pointsCalculated);
        if (session.isPresent()) {
            points.setSession(session.get());
        } else {
            points.setSession(null);
        }
        points.setParticipant(participant);
        Predictions predictions1 = predictionsRepository.checkPredictionExistence(grandPrixId, sessionId, username).get(0);
        if (grandPrix.isPresent()) {
            predictions1.setGrandPrix(grandPrix.get());
        } else {
            predictions1.setGrandPrix(null);
        }
        predictions1.setParticipant(participant);
        if (session.isPresent()) {
            predictions1.setSession(session.get());
        } else {
            predictions1.setSession(null);
        }
        points.setPredictions(predictions1);
        pointsRepository.save(points);

        return ResponseEntity.ok(String.valueOf(pointsCalculated));

    }
}
