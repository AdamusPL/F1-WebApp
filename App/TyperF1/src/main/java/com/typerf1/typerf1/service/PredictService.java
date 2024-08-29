package com.typerf1.typerf1.service;

import com.typerf1.typerf1.model.*;
import com.typerf1.typerf1.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import com.typerf1.typerf1.model.Predictions;

import com.typerf1.typerf1.tools.PointsCalculator;

@Service
public class PredictService {

    private final GrandPrixRepository grandPrixRepository;
    private final SessionRepository sessionRepository;
    private final PredictionsRepository predictionsRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public PredictService(GrandPrixRepository grandPrixRepository, SessionRepository sessionRepository,
                          PredictionsRepository predictionsRepository, ParticipantRepository participantRepository) {
        this.grandPrixRepository = grandPrixRepository;
        this.sessionRepository = sessionRepository;
        this.predictionsRepository = predictionsRepository;
        this.participantRepository = participantRepository;
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
        Predictions predictions = predictionsList.get(0);
        predictions.setParticipant(null);
        predictions.setSession(null);
        predictions.setGrandPrix(null);
        return predictions;
    }

    public double F1APIQualifyingParser(int grandPrixId, int sessionId, String username, int year){
        boolean joker = false;
        Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);

        //page with api with F1 race results
        String url = "https://ergast.com/api/f1/" + year + "/" + grandPrixId + "/qualifying";
        RestTemplate restTemplate = new RestTemplate();
        List<String> driverStandings = new ArrayList<>();

        Object[] results = restTemplate.getForObject(url, Object[].class);
        // Step 1: Access the first element of the array, cast it to LinkedHashMap
        LinkedHashMap<?, ?> rootMap = (LinkedHashMap<?, ?>) results[1];

        // Step 2: Access the "Race" LinkedHashMap
        LinkedHashMap<?, ?> raceMap = (LinkedHashMap<?, ?>) rootMap.get("Race");

        // Step 3: Access the "ResultsList" LinkedHashMap
        LinkedHashMap<?, ?> resultsListMap = (LinkedHashMap<?, ?>) raceMap.get("QualifyingList");

        // Step 4: Access the "Result" LinkedHashMap
        ArrayList<?> resultsArray = (ArrayList<?>) resultsListMap.get("QualifyingResult");

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

        PointsCalculator pointsCalculator = new PointsCalculator(driverStandings, participantPredictions, joker);

        return pointsCalculator.countPointsFromQualifying();
    }

    public double F1APIRaceParser(int grandPrixId, int sessionId, String username, int year) throws NoSuchFieldException, IllegalAccessException {
        boolean joker = false;
        Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);

        //page with api with F1 race results (standings)
        String url = "https://ergast.com/api/f1/" + year + "/" + grandPrixId + "/results";
        RestTemplate restTemplate = new RestTemplate();
        List<String> driverStandings = new ArrayList<>();

        Object[] results = restTemplate.getForObject(url, Object[].class);
        // Step 1: Access the first element of the array, cast it to LinkedHashMap
        LinkedHashMap<?, ?> rootMap = (LinkedHashMap<?, ?>) results[1];

        // Step 2: Access the "Race" LinkedHashMap
        LinkedHashMap<?, ?> raceMap = (LinkedHashMap<?, ?>) rootMap.get("Race");

        // Step 3: Access the "ResultsList" LinkedHashMap
        LinkedHashMap<?, ?> resultsListMap = (LinkedHashMap<?, ?>) raceMap.get("ResultsList");

        // Step 4: Access the "Result" LinkedHashMap
        ArrayList<?> resultsArray = (ArrayList<?>) resultsListMap.get("Result");

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

        PointsCalculator pointsCalculator = new PointsCalculator(driverStandings, participantPredictions, joker);

        //page with the fastest laps in race
        String urlFastestLap = "http://ergast.com/api/f1/" + year + "/" + grandPrixId + "/fastest/" + 1 + "/results";
        RestTemplate restTemplateFastestLap = new RestTemplate();

        Object[] fastestLaps = restTemplate.getForObject(url, Object[].class);

        // Step 1: Access the first element of the array, cast it to LinkedHashMap
        LinkedHashMap<?, ?> rootMapFL = (LinkedHashMap<?, ?>) results[1];

        // Step 2: Access the "Race" LinkedHashMap
        LinkedHashMap<?, ?> raceMapFL = (LinkedHashMap<?, ?>) rootMapFL.get("Race");

        // Step 3: Access the "ResultsList" LinkedHashMap
        LinkedHashMap<?, ?> resultsListMapFL = (LinkedHashMap<?, ?>) raceMapFL.get("ResultsList");

        // Step 4: Access the "Result" LinkedHashMap
        ArrayList<?> resultsArrayFL = (ArrayList<?>) resultsListMapFL.get("Result");

        LinkedHashMap<?, ?> resultMap = (LinkedHashMap<?, ?>) resultsArrayFL.get(0);

        LinkedHashMap<?, ?> resultDriver = (LinkedHashMap<?, ?>) resultMap.get("Driver");

        String actualFastestLap = (String) resultDriver.get("FamilyName");

        return pointsCalculator.countPointsFromRace(predictions.getFastestLap(), actualFastestLap);
    }
}
