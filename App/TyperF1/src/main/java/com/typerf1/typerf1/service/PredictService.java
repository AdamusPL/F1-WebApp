package com.typerf1.typerf1.service;

import com.typerf1.typerf1.model.*;
import com.typerf1.typerf1.repository.*;
import com.typerf1.typerf1.tools.URL;
import com.typerf1.typerf1.tools.UrlFromTxtParser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.typerf1.typerf1.model.Predictions;

import com.typerf1.typerf1.tools.PointsCalculator;
import org.springframework.web.util.UriComponentsBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

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

    public ResponseEntity<String> postPredictions(int grandPrixId, int sessionId, String username, boolean joker, Predictions predictions) {
        GrandPrix grandPrix = grandPrixRepository.findById(grandPrixId)
                .orElseThrow(() -> new EntityNotFoundException("GrandPrix not found with id: " + grandPrixId));
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id: " + sessionId));
        Participant participant = participantRepository.getParticipantByParticipantLoginDataUsername(username).get(0);
        predictions.setGrandPrix(grandPrix);
        predictions.setSession(session);
        predictions.setParticipant(participant);
        if(joker && predictions.getGrandPrix().getJoker() == null){
            Joker jokerObject = new Joker();
            jokerObject.setParticipant(participant);
            jokerObject.setGrandPrix(grandPrix);
            jokerRepository.save(jokerObject);
        }

        predictionsRepository.save(predictions);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Predictions> checkPredictionsExistence(String sessionType, int year, int grandPrixId, int sessionId, String username) throws ParseException {
        List<Predictions> predictionsList = predictionsRepository.checkPredictionExistence(grandPrixId, sessionId, username);
        if (predictionsList.size() != 0) {
            Predictions predictions = predictionsList.get(0);
            predictions.setParticipant(null);
            predictions.setSession(null);
            predictions.setGrandPrix(null);
            return ResponseEntity.ok(predictions);
        } else {

            //still have to handle how to get sprint begin time
            if(sessionType.equals("S")){
                return ResponseEntity.noContent().build();
            }

            //check if user can still post predictions
            boolean isAbleToPost;

            if(sessionType.equals("R")) {
                isAbleToPost = checkBeginningTimeOfRace(year, grandPrixId);
            }
            else{
                isAbleToPost = checkBeginningTimeOfQualifying(year, grandPrixId);
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
        return predictionsList.get(0);
    }

    public ResponseEntity<String> F1APIQualifyingParser(int grandPrixId, int sessionId, String username, int year) throws ParseException {
        boolean joker = false;
        Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);
        if(predictions.getGrandPrix().getJoker() != null){
            joker = true;
        }

        double pointsCalculated;

        //if points were already calculated
        if (predictions.getPoints() != null) {
            return ResponseEntity.ok(predictions.getPoints().getNumber().toString());
        }

        //page with api with F1 race results
        String url = "https://ergast.com/api/f1/" + year + "/" + grandPrixId + "/qualifying";
        LinkedHashMap<?, ?> raceMap = getTreeToRaces(url);

        if (raceMap == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // Step 3: Access the "ResultsList" LinkedHashMap
        LinkedHashMap<?, ?> resultsListMap = (LinkedHashMap<?, ?>) raceMap.get("QualifyingList");

        // Step 4: Access the "Result" LinkedHashMap
        ArrayList<?> resultsArray = (ArrayList<?>) resultsListMap.get("QualifyingResult");

        List<String> driverStandings = parseSurnames(resultsArray);

        PointsCalculator pointsCalculator = initPointsCalculator(predictions, driverStandings, joker);

        pointsCalculated = pointsCalculator.countPointsFromQualifying();

        return updatePredictionsInDB(grandPrixId, sessionId, username, predictions, pointsCalculated);
    }

    private ResponseEntity<String> updatePredictionsInDB(int grandPrixId, int sessionId, String username, Predictions predictions, double pointsCalculated) {
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

    private boolean checkBeginningTimeOfRace(int year, int grandPrixId) throws ParseException {
        String url = "https://ergast.com/api/f1/" + year;

        RestTemplate restTemplate = new RestTemplate();

        Object[] results = restTemplate.getForObject(url, Object[].class);
        // Step 1: Access the first element of the array, cast it to LinkedHashMap
        LinkedHashMap<?, ?> rootMap = (LinkedHashMap<?, ?>) results[1];

        ArrayList<?> racesArray = (ArrayList<?>) rootMap.get("Race");

        LinkedHashMap<?, ?> raceMap = (LinkedHashMap<?, ?>) racesArray.get(grandPrixId - 1);

        // Date and time of the beginning of the session
        String beginningDate = (String) raceMap.get("Date");
        String beginningTime = (String) raceMap.get("Time");

        // Create date object
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Ensure date parsing in UTC
        Date dateToCompare = dateFormatter.parse(beginningDate);

        // Current date and hour in computer (system default time zone)
        Date currentDate = new Date();

        // Check if it's not too late to post predictions based on the date
        if (currentDate.after(dateToCompare)) {
            return false;
        }

        // Create combined date-time string for parsing
        String dateTimeString = beginningDate + " " + beginningTime;

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

        return true;
    }

    private boolean checkBeginningTimeOfQualifying(int year, int grandPrixId) throws ParseException {
        String url = "https://ergast.com/api/f1/" + year + "/" + grandPrixId + ".json";

        RestTemplate restTemplate = new RestTemplate();

        // Build the URI with placeholders
        String uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(Map.of("season", year, "round", grandPrixId))
                .toString();

        // Fetch data from the API
        Map response = restTemplate.getForObject(uri, Map.class);
        String beginningDate = "";
        String beginningTime = "";

        if (response != null) {
            Map raceTable = (Map) response.get("MRData");
            Map raceData = (Map) ((Map) ((List) ((Map) raceTable.get("RaceTable")).get("Races")).get(0)).get("Qualifying");

            if (raceData != null) {
                beginningDate = (String) raceData.get("date");
                beginningTime = (String) raceData.get("time");
            }
        }

        // Create date object
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Ensure date parsing in UTC
        Date dateToCompare = dateFormatter.parse(beginningDate);

        // Current date and hour in computer (system default time zone)
        Date currentDate = new Date();

        // Check if it's not too late to post predictions based on the date
        if (currentDate.after(dateToCompare)) {
            return false;
        }

        // Create combined date-time string for parsing
        String dateTimeString = beginningDate + " " + beginningTime;

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

        return true;
    }

    public ResponseEntity<String> F1APIRaceParser(int grandPrixId, int sessionId, String username, int year) {
        boolean joker = false;
        Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);
        if(predictions.getGrandPrix().getJoker() != null){
            joker = true;
        }

        double pointsCalculated;

        //if points were already calculated
        if (predictions.getPoints() != null) {
            return ResponseEntity.ok(predictions.getPoints().getNumber().toString());
        }

        //page with api with F1 race results (standings)
        String url = "https://ergast.com/api/f1/" + year + "/" + grandPrixId + "/results";
        LinkedHashMap<?, ?> raceMap = getTreeToRaces(url);

        //if race hasn't finished yet
        if (raceMap == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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

        return updatePredictionsInDB(grandPrixId, sessionId, username, predictions, pointsCalculated);
    }

    public ResponseEntity<String> sprintSeleniumParser(int grandPrixId, int sessionId, String username, int year, String grandPrixName) {
        try {
            boolean joker = false;
            Predictions predictions = getParticipantPredictions(grandPrixId, sessionId, username);
            if (predictions.getGrandPrix().getJoker() != null) {
                joker = true;
            }

            double pointsCalculated;

            //if points were already calculated
            if (predictions.getPoints() != null) {
                return ResponseEntity.ok(predictions.getPoints().getNumber().toString());
            }

            ArrayList<String> driverArrayList = new ArrayList<>();
            WebDriver driver;
            System.setProperty("webdriver.firefox.marionette","C:\\apps\\geckodriver.exe");

            // Create an instance of FirefoxOptions
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless=new");

            driver = new FirefoxDriver(options);

            String file = "2024_s.txt";
            UrlFromTxtParser urlFromTxtParser = new UrlFromTxtParser();
            ArrayList<URL> urls = urlFromTxtParser.add(file, resourceLoader);

            String baseUrl = null;

            grandPrixName = grandPrixName.toLowerCase();
            grandPrixName = grandPrixName.replace(" ", "-");

            for(URL url : urls){
                if(url.getGpName().equals(grandPrixName)){
                    baseUrl = url.getUrl();
                    baseUrl += "/sprint-results";
                    break;
                }
            }

            if(baseUrl != null) {
                driver.get(baseUrl);

                List<WebElement> sth = driver.findElements(By.className("max-tablet:hidden"));

                for (WebElement webElement : sth) {
                    String surname = webElement.getText();
                    driverArrayList.add(surname);
                }

                PointsCalculator pointsCalculator = initPointsCalculator(predictions, driverArrayList, joker);
                pointsCalculated = pointsCalculator.countPointsFromSprint();

                return updatePredictionsInDB(grandPrixId, sessionId, username, predictions, pointsCalculated);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
