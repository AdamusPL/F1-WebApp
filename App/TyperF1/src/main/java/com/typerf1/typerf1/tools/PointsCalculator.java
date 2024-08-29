package com.typerf1.typerf1.tools;

import java.util.ArrayList;
import java.util.List;

public class PointsCalculator {

    private List<String> driverStandings;
    private List<String> participantPredictions;
    private double points;
    private boolean joker;

    public PointsCalculator(List<String> driverStandings, List<String> participantPredictions, boolean joker) {
        this.driverStandings = driverStandings;
        this.participantPredictions = participantPredictions;
        this.points = 0;
        this.joker = joker;
    }

    public double countPointsFromRace(String fastestLap, String actualFastestLap) {
        points += getPoints();

        //if fastest lap was guessed
        if(fastestLap.equals(actualFastestLap)){
            points += 1;
        }

        points *= 2; //because it's race

        if (joker) {
            points *= 2;
            //here also should consider adding number of used jokers in entity
        }

        return points;
    }

    public double countPointsFromQualifying() {

        points += getPoints();

        if (joker) {
            points *= 2;
        }

        return points;
    }

    public double countPointsFromSprint() {

        points = getPoints();

        if (joker) {
            points *= 2;
            //here also should consider adding number of used jokers in entity
        }

        return points;
    }

    //sprint shootout isn't predicted anymore by participants
    public double countPointsFromSprintShootout() {

        points = getPoints();

        points *= 0.5; //because it's sprint shootout

        if (joker) {
            points *= 2;
        }

        return points;
    }

    private double getPoints() {
        int actualIndex = 0;
        for (String actualDriver : driverStandings) {
            int predictionsIndex = 0;
            for (String participantPrediction : participantPredictions) {
                if (participantPrediction.equals(actualDriver)) {
                    if (actualIndex == predictionsIndex) {
                        switch (predictionsIndex + 1) {
                            case 1:
                                points += 3;
                                break;
                            case 2:
                                points += 2;
                                break;
                            case 3:
                                points += 1;
                                break;
                        }
                        points += 2;
                    } else if (predictionsIndex == actualIndex - 1 || predictionsIndex == actualIndex + 1) {
                        points += 1;
                    }
                }
                predictionsIndex++;
            }
            System.out.println(points);
            actualIndex++;
        }
        return points;
    }

}