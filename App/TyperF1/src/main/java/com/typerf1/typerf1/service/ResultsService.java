package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.joker.JokersUsed;
import com.typerf1.typerf1.dto.plot.PlotDto;
import com.typerf1.typerf1.dto.plot.ScoresDto;
import com.typerf1.typerf1.dto.season.SeasonScore;
import com.typerf1.typerf1.dto.season.SeasonScoreWithJokers;
import com.typerf1.typerf1.repository.ResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ResultsService {
    private final ResultsRepository resultsRepository;

    @Autowired
    public ResultsService(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    public List<SeasonScoreWithJokers> getParticipantStandings(Integer year) {
        List<SeasonScore> pointsList = resultsRepository.getParticipantStandings(year);
        List<JokersUsed> jokersList = resultsRepository.getParticipantJokers(year);
        List<SeasonScoreWithJokers> seasonScoreWithJokersList = new ArrayList<>();

        for (SeasonScore score : pointsList) {
            Long numberOfJokersUsed = 0L;
            for (JokersUsed jokersUsed : jokersList) {
                if (jokersUsed.getParticipantName().equals(score.getParticipantName()) && jokersUsed.getParticipantSurname().equals(score.getParticipantSurname())) {
                    numberOfJokersUsed = jokersUsed.getNumberOfJokersUsed();
                }
            }
            SeasonScoreWithJokers seasonScoreWithJokers = new SeasonScoreWithJokers(score.getParticipantName(), score.getParticipantSurname(), score.getPointsSum(), numberOfJokersUsed);
            seasonScoreWithJokersList.add(seasonScoreWithJokers);
        }

        return seasonScoreWithJokersList;
    }

    public List<PlotDto> getDataPlot() {
        var list = resultsRepository.getPlotData();
        var listOfPlots = new ArrayList<PlotDto>();

        for (ScoresDto element : list) {
            var name = element.getParticipantName() + " " + element.getParticipantSurname();
            if (!listOfPlots.isEmpty()) {
                boolean found = false;
                int index = 0;
                for (var el : listOfPlots) {
                    if ((el.getParticipantName() + " " + el.getParticipantSurname()).equals(name)) {
                        found = true;
                        break;
                    }
                    index++;
                }
                if (found) {
                    var scores = listOfPlots.get(index).getScores();
                    var sessions = listOfPlots.get(index).getSessions();
                    var size = listOfPlots.get(index).getScores().size();
                    scores.add(scores.get(size - 1) + element.getPoints());
                    sessions.add(element.getGpName() + ", " + element.getSessionName());
                    listOfPlots.get(index).setScores(scores);
                    listOfPlots.get(index).setSessions(sessions);
                } else {
                    PlotDto plotDto = new PlotDto(element.getParticipantName(), element.getParticipantSurname(), new ArrayList<>(Arrays.asList(element.getGpName() + ", " + element.getSessionName())), new ArrayList<>(Arrays.asList(element.getPoints())));
                    listOfPlots.add(plotDto);
                }
            } else {
                PlotDto plotDto = new PlotDto(element.getParticipantName(), element.getParticipantSurname(), new ArrayList<>(Arrays.asList(element.getGpName() + ", " + element.getSessionName())), new ArrayList<>(Arrays.asList(element.getPoints())));
                listOfPlots.add(plotDto);
            }
        }

        return listOfPlots;
    }
}
