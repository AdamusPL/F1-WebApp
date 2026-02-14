package com.typerf1.typerf1.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.typerf1.typerf1.model.*;
import com.typerf1.typerf1.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.typerf1.typerf1.model.Predictions;

import com.typerf1.typerf1.tools.PointsCalculator;

import static com.typerf1.typerf1.tools.StringUtils.stripAccents;

@Service
public class PredictService {

    @Autowired
    private ResourceLoader resourceLoader;

    private final GrandPrixRepository grandPrixRepository;
    private final SessionRepository sessionRepository;
    private final PredictionsRepository predictionsRepository;
    private final ParticipantRepository participantRepository;
    private final PointsRepository pointsRepository;
    private final JokerRepository jokerRepository;

    @Autowired
    public PredictService(GrandPrixRepository grandPrixRepository, SessionRepository sessionRepository,
                          PredictionsRepository predictionsRepository, ParticipantRepository participantRepository,
                          PointsRepository pointsRepository, JokerRepository jokerRepository) {
        this.grandPrixRepository = grandPrixRepository;
        this.sessionRepository = sessionRepository;
        this.predictionsRepository = predictionsRepository;
        this.participantRepository = participantRepository;
        this.pointsRepository = pointsRepository;
        this.jokerRepository = jokerRepository;
    }

    public List<GrandPrix> getThisYearGrandPrix(int year) {
        return grandPrixRepository.getThisYearGrandPrixWeekends(year);
    }

    public List<Session> getSessionsOfGrandPrix(int grandPrixId) {
        return sessionRepository.getSessionsFromThatGrandPrix(grandPrixId);
    }

    public ResponseEntity<String> postPredictions(int grandPrixId, int sessionId, boolean joker, Predictions predictions) {
        GrandPrix grandPrix = grandPrixRepository.findById(grandPrixId)
                .orElseThrow(() -> new EntityNotFoundException("GrandPrix not found with id: " + grandPrixId));
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id: " + sessionId));
        Participant participant = participantRepository.getParticipantByParticipantLoginDataUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).getFirst();
        predictions.setGrandPrix(grandPrix);
        predictions.setSession(session);
        predictions.setParticipant(participant);
        if (joker && predictions.getGrandPrix().getJoker() == null) {
            Joker jokerObject = new Joker();
            jokerObject.setParticipant(participant);
            jokerObject.setGrandPrix(grandPrix);
            jokerRepository.save(jokerObject);
        }

        predictionsRepository.save(predictions);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Predictions> checkPredictionsExistence(String sessionType, int year, int grandPrixId, int sessionId) throws ParseException {
        List<Predictions> predictionsList = predictionsRepository.checkPredictionExistence(grandPrixId, sessionId, SecurityContextHolder.getContext().getAuthentication().getName());
        if (!predictionsList.isEmpty()) {
            Predictions predictions = predictionsList.getFirst();
            predictions.setParticipant(null);
            predictions.setSession(null);
            predictions.setGrandPrix(null);
            return ResponseEntity.ok(predictions);
        } else {
            //check if user can still post predictions
            boolean isAbleToPost;

            if (sessionType.equals("R")) {
                isAbleToPost = checkBeginningTimeOfRace(year, grandPrixId);
            } else if (sessionType.equals("Q")) {
                isAbleToPost = checkBeginningTimeOfQualifying(year, grandPrixId);
            } else {
                isAbleToPost = checkBeginningTimeOfSprint(year, grandPrixId);
            }

            //if session has already begun
            if (!isAbleToPost) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
            return ResponseEntity.noContent().build();
        }
    }

    public Predictions getParticipantPredictions(int grandPrixId, int sessionId, String username) {
        List<Predictions> predictionsList = predictionsRepository.checkPredictionExistence(grandPrixId, sessionId, username);
        return predictionsList.getFirst();
    }

    public ResponseEntity<String> F1APIQualifyingParser(int grandPrixId, int sessionId, int year) {
        boolean joker = false;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);
        if (predictions.getGrandPrix().getJoker() != null) {
            joker = true;
        }

        double pointsCalculated;

        //if points were already calculated
        if (predictions.getPoints() != null) {
            return ResponseEntity.ok(predictions.getPoints().getNumber().toString());
        }

        //page with api with F1 race results
        String url = "https://api.jolpi.ca/ergast/f1/" + year + "/" + grandPrixId + "/qualifying.json";
        List<String> driverStandings = getQualifyingResults(url);

        if (driverStandings == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        PointsCalculator pointsCalculator = initPointsCalculator(predictions, driverStandings, joker);

        pointsCalculated = pointsCalculator.countPointsFromQualifying();

        return updatePredictionsInDB(grandPrixId, sessionId, username, predictions, pointsCalculated);
    }

    private ResponseEntity<String> updatePredictionsInDB(int grandPrixId, int sessionId, String username, Predictions predictions, double pointsCalculated) {
        Points points = new Points(pointsCalculated);
        points.setParticipant(predictions.getParticipant());
        points.setSession(predictions.getSession());
        Predictions predictions1 = predictionsRepository.checkPredictionExistence(grandPrixId, sessionId, username).getFirst();
        points.setPredictions(predictions1);

        predictions1.setPoints(points);
        pointsRepository.save(points);

        return ResponseEntity.ok(String.valueOf(pointsCalculated));
    }

    public record Driver(String familyName) {
    }

    private List<String> getQualifyingResults(String url) {
        RestClient restClient = RestClient.create();

        F1ResponseSprint response = restClient.get()
                .uri(url)
                .retrieve()
                .body(F1ResponseSprint.class);

        if (response != null && !response.mrData().raceTable().races().isEmpty()) {
            List<SprintResult> results = response.mrData().raceTable().races().getFirst().results();
            return results.stream()
                    .map(result -> stripAccents(result.driver().familyName())) // Extract the field
                    .toList();
        }

        return Collections.emptyList();
    }

    public record F1ResponseRace(@JsonProperty("MRData") MRDataRace mrData) {
    }

    public record MRDataRace(@JsonProperty("RaceTable") RaceTable raceTable) {
    }

    public record RaceTable(@JsonProperty("Races") List<Race> races) {
    }

    public record Race(
            @JsonProperty("Results") List<RaceResult> results
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FastestLap(
            @JsonProperty("rank") String rank
    ) {}

    public record RaceResult(
       @JsonProperty("Driver") Driver driver,
       @JsonProperty("FastestLap") FastestLap fastestLap
    ) {}

    private LinkedHashMap<String, Boolean> getRaceResults(String url) {
        RestClient restClient = RestClient.create();

        F1ResponseRace response = restClient.get()
                .uri(url)
                .retrieve()
                .body(F1ResponseRace.class);

        if (response != null && !response.mrData().raceTable().races().isEmpty()) {
            List<RaceResult> results = response.mrData().raceTable().races().getFirst().results();
            return results.stream()
                    .collect(Collectors.toMap(
                            result -> stripAccents(result.driver().familyName()),
                            result -> result.fastestLap() != null && result.fastestLap().rank().equals("1"),
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    ));// Extract the field
        }

        return new LinkedHashMap<>();
    }

    public List<String> getAllSurnames(Map<String, Boolean> resultsMap) {
        return new ArrayList<>(resultsMap.keySet());
    }

    public ResponseEntity<String> F1APIRaceParser(int grandPrixId, int sessionId, int year) {
        boolean joker = false;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);
        if (predictions.getGrandPrix().getJoker() != null) {
            joker = true;
        }

        double pointsCalculated;

        //if points were already calculated
        if (predictions.getPoints() != null) {
            return ResponseEntity.ok(predictions.getPoints().getNumber().toString());
        }

        //page with api with F1 race results (standings)
        String url = "https://api.jolpi.ca/ergast/f1/" + year + "/" + grandPrixId + "/results.json";
        Map<String, Boolean> raceResults = getRaceResults(url);

        String actualFastestLap = raceResults.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("No one");

        List<String> driverStandings = getAllSurnames(raceResults);

        if (driverStandings == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        PointsCalculator pointsCalculator = initPointsCalculator(predictions, driverStandings, joker);

        pointsCalculated = pointsCalculator.countPointsFromRace(predictions.getFastestLap(), actualFastestLap);

        return updatePredictionsInDB(grandPrixId, sessionId, username, predictions, pointsCalculated);
    }

    public record F1ResponseSprint(@JsonProperty("MRData") MRDataSprint mrData) {
    }

    public record MRDataSprint(@JsonProperty("RaceTable") SprintTable raceTable) {
    }

    public record SprintTable(@JsonProperty("Races") List<Sprint> races) {
    }

    public record Sprint(
            @JsonProperty("SprintResults") List<SprintResult> results
    ) {}

    public record SprintResult(
            @JsonProperty("Driver") Driver driver
    ) {}

    public ResponseEntity<String> F1APISprintParser(int grandPrixId, int sessionId, int year) {
        boolean joker = false;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);
        if (predictions.getGrandPrix().getJoker() != null) {
            joker = true;
        }

        double pointsCalculated;

        //if points were already calculated
        if (predictions.getPoints() != null) {
            return ResponseEntity.ok(predictions.getPoints().getNumber().toString());
        }

        //page with api with F1 race results (standings)
        String url = "https://api.jolpi.ca/ergast/f1/" + year + "/" + grandPrixId + "/sprint.json";
        List<String> sprintResults = getQualifyingResults(url);
        PointsCalculator pointsCalculator = initPointsCalculator(predictions, sprintResults, joker);
        pointsCalculated = pointsCalculator.countPointsFromSprint();

        return updatePredictionsInDB(grandPrixId, sessionId, username, predictions, pointsCalculated);
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

    public record F1ScheduleResponse(@JsonProperty("MRData") MRDataSchedule mrData) {}

    public record MRDataSchedule(@JsonProperty("RaceTable") RaceTableSchedule raceTable) {}

    public record RaceTableSchedule(@JsonProperty("Races") List<RaceSchedule> races) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RaceSchedule(
            @JsonProperty("date") String raceDate, // Date of the RACE
            @JsonProperty("time") String raceTime // Time of the RACE
    ) {}

    private boolean checkBeginningTimeOfRace(int year, int grandPrixId) throws ParseException {
        String url = "https://api.jolpi.ca/ergast/f1/" + year + "/" + grandPrixId + ".json";

        RestClient restClient = RestClient.create();

        var response = restClient.get()
                .uri(url)
                .retrieve()
                .body(F1ScheduleResponse.class);

        if (response != null && !response.mrData().raceTable().races().isEmpty()) {
            String date = response.mrData().raceTable().races().getFirst().raceDate();
            String time = response.mrData().raceTable().races().getFirst().raceTime();

            // Create date object
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Ensure date parsing in UTC
            Date dateToCompare = dateFormatter.parse(date);

            // Current date and hour in computer (system default time zone)
            Date currentDate = new Date();

            // Check if it's not too late to post predictions based on the date
            if (currentDate.after(dateToCompare)) {
                return false;
            }

            // Create combined date-time string for parsing
            String dateTimeString = date + " " + time;

            // Parse the date and time together with UTC timezone
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
            dateTimeFormatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Parse as UTC
            Date dateTimeToCompare = dateTimeFormatter.parse(dateTimeString);

            // Create a Calendar instance for the parsed time in UTC
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(dateTimeToCompare);

            // Subtract 30 minutes from the race start time
            calendar.add(Calendar.MINUTE, -30);
            Date adjustedTime = calendar.getTime(); // New time after subtracting 30 minutes

            // Get the current time in UTC for comparison
            Calendar currentCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            currentCal.setTime(currentDate);

            // Check if it's not too late to post predictions based on the adjusted time
            if (currentCal.getTime().after(adjustedTime)) {
                return false;
            }
        }

        return true;
    }

    public record F1ScheduleQualifyingResponse(@JsonProperty("MRData") MRDataQualifyingSchedule mrData) {}

    public record MRDataQualifyingSchedule(@JsonProperty("RaceTable") QualifyingTableSchedule raceTable) {}

    public record QualifyingTableSchedule(@JsonProperty("Races") List<QualifyingSchedule> races) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record QualifyingSchedule(
            @JsonProperty("raceName") String raceName,
            @JsonProperty("date") String raceDate, // Date of the RACE
            @JsonProperty("time") String raceTime, // Time of the RACE
            @JsonProperty("Qualifying") SessionSchedule qualifying // <--- This is what you want
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SessionSchedule(
            @JsonProperty("date") String date,
            @JsonProperty("time") String time
    ) {}

    private boolean checkBeginningTimeOfQualifying(int year, int grandPrixId) throws ParseException {
        String url = "https://api.jolpi.ca/ergast/f1/" + year + "/" + grandPrixId + ".json";

        var restClient = RestClient.create();

        var response = restClient.get()
                .uri(url)
                .retrieve()
                .body(F1ScheduleQualifyingResponse.class);

        if (response != null && !response.mrData().raceTable().races().isEmpty()) {
            String date = response.mrData().raceTable().races().getFirst().qualifying().date();
            String time = response.mrData().raceTable().races().getFirst().qualifying().time();

            // Create date object
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Ensure date parsing in UTC
            Date dateToCompare = dateFormatter.parse(date);

            // Current date and hour in computer (system default time zone)
            Date currentDate = new Date();

            // Check if it's not too late to post predictions based on the date
            if (currentDate.after(dateToCompare)) {
                return false;
            }

            // Create combined date-time string for parsing
            String dateTimeString = date + " " + time;

            // Parse the date and time together with UTC timezone
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
            dateTimeFormatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Parse as UTC
            Date dateTimeToCompare = dateTimeFormatter.parse(dateTimeString);

            // Create a Calendar instance for the parsed time in UTC
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(dateTimeToCompare);

            // Subtract 30 minutes from the race start time
            calendar.add(Calendar.MINUTE, -30);
            Date adjustedTime = calendar.getTime(); // New time after subtracting 30 minutes

            // Get the current time in UTC for comparison
            Calendar currentCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            currentCal.setTime(currentDate);

            // Check if it's not too late to post predictions based on the adjusted time
            if (currentCal.getTime().after(adjustedTime)) {
                return false;
            }
        }

        return true;
    }

    public record F1ScheduleSprintResponse(@JsonProperty("MRData") MRDataSprintSchedule mrData) {}

    public record MRDataSprintSchedule(@JsonProperty("RaceTable") SprintTableSchedule raceTable) {}

    public record SprintTableSchedule(@JsonProperty("Races") List<SprintSchedule> races) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SprintSchedule(
            @JsonProperty("raceName") String raceName,
            @JsonProperty("date") String raceDate, // Date of the RACE
            @JsonProperty("time") String raceTime, // Time of the RACE
            @JsonProperty("Sprint") SprintSessionSchedule sprint // <--- This is what you want
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SprintSessionSchedule(
            @JsonProperty("date") String date,
            @JsonProperty("time") String time
    ) {}

    private boolean checkBeginningTimeOfSprint(int year, int grandPrixId) throws ParseException {
        String url = "https://api.jolpi.ca/ergast/f1/" + year + "/" + grandPrixId + ".json";

        var restClient = RestClient.create();

        var response = restClient.get()
                .uri(url)
                .retrieve()
                .body(F1ScheduleSprintResponse.class);

        if (response != null && !response.mrData().raceTable().races().isEmpty()) {
            String date = response.mrData().raceTable().races().getFirst().sprint().date();
            String time = response.mrData().raceTable().races().getFirst().sprint().time();

            // Create date object
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Ensure date parsing in UTC
            Date dateToCompare = dateFormatter.parse(date);

            // Current date and hour in computer (system default time zone)
            Date currentDate = new Date();

            // Check if it's not too late to post predictions based on the date
            if (currentDate.after(dateToCompare)) {
                return false;
            }

            // Create combined date-time string for parsing
            String dateTimeString = date + " " + time;

            // Parse the date and time together with UTC timezone
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
            dateTimeFormatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Parse as UTC
            Date dateTimeToCompare = dateTimeFormatter.parse(dateTimeString);

            // Create a Calendar instance for the parsed time in UTC
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(dateTimeToCompare);

            // Subtract 30 minutes from the race start time
            calendar.add(Calendar.MINUTE, -30);
            Date adjustedTime = calendar.getTime(); // New time after subtracting 30 minutes

            // Get the current time in UTC for comparison
            Calendar currentCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            currentCal.setTime(currentDate);

            // Check if it's not too late to post predictions based on the adjusted time
            if (currentCal.getTime().after(adjustedTime)) {
                return false;
            }
        }

        return true;
    }
}
