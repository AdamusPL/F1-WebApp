package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.grandprix.GrandPrixScore;
import com.typerf1.typerf1.dto.grandprix.GrandPrixScoreWithJokers;
import com.typerf1.typerf1.dto.joker.UsedJokersGP;
import com.typerf1.typerf1.dto.points.Score;
import com.typerf1.typerf1.dto.standings.GrandPrixDto;
import com.typerf1.typerf1.dto.standings.SessionDto;
import com.typerf1.typerf1.repository.StandingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

@Service
public class StandingsService {
    private final StandingsRepository standingsRepository;

    @Autowired
    public StandingsService(StandingsRepository standingsRepository) {
        this.standingsRepository = standingsRepository;
    }

    public List<GrandPrixDto> getScores(Integer year) {
        List<Score> scoreList = standingsRepository.findSeasonScores(year);
        var scores = scoreList
                .stream()
                .collect(collectingAndThen(groupingBy(Score::getGrandPrixName,
                                collectingAndThen(groupingBy(Score::getSessionName),
                                        sessionName -> sessionName.entrySet().stream()
                                                .map(e -> new SessionDto(e.getKey(), e.getValue()))
                                                .toList()
                                )
                        ),
                        gpMap -> gpMap.entrySet().stream()
                                .map(e -> new GrandPrixDto(e.getKey(), e.getValue()))
                                .sorted(comparing(GrandPrixDto::getName))
                                .toList()));

//        String currentGrandPrixName = "";
//        String current = "";
//        for (Score score : scoreList) {
//            if (!score.getGrandPrixName().equals(currentGrandPrixName)) {
//                currentGrandPrixName = score.getGrandPrixName();
//            }
//        }
//        List<JokersUsage> usedJokersGPList = standingsRepository.findJokerUsageInSeasonScores(year);
//        List<ScoreWithJokers> scoreWithJokersList = new ArrayList<>();
//
//        for (Score score : scoreList) {
//            Double numberOfJokersUsed = 0D;
//            for (JokersUsage jokersUsed : usedJokersGPList) {
//                if (jokersUsed.getParticipantName().equals(score.getParticipantName()) && jokersUsed.getParticipantSurname().equals(score.getParticipantSurname()) && jokersUsed.getGrandPrixName().equals(score.getGrandPrixName())) {
//                    numberOfJokersUsed = 1D;
//                }
//            }
//            ScoreWithJokers scoreWithJokers = new ScoreWithJokers(score.getGrandPrixName(), score.getSessionName(), score.getParticipantName(), score.getParticipantSurname(), score.getPoints(), numberOfJokersUsed);
//            scoreWithJokersList.add(scoreWithJokers);
//        }

        return scores;
    }

    public List<GrandPrixScoreWithJokers> getGrandPrixSummaryScores(Integer year, String grandPrixName) {
        List<GrandPrixScore> grandPrixScoreList = standingsRepository.findGrandPrixSummaryScores(year, grandPrixName);
        List<UsedJokersGP> usedJokersGPList = standingsRepository.findJokersUsedOnGPs(year, grandPrixName);
        List<GrandPrixScoreWithJokers> grandPrixScoreWithJokersList = new ArrayList<>();

        for (GrandPrixScore score : grandPrixScoreList) {
            Long numberOfJokersUsed = 0L;
            for (UsedJokersGP jokersUsed : usedJokersGPList) {
                if (jokersUsed.getParticipantName().equals(score.getParticipantName()) && jokersUsed.getParticipantSurname().equals(score.getParticipantSurname()) && jokersUsed.getGrandPrixName().equals(score.getGrandPrixName())) {
                    numberOfJokersUsed = jokersUsed.getNumberOfJokersUsed();
                }
            }
            GrandPrixScoreWithJokers grandPrixScoreWithJokers = new GrandPrixScoreWithJokers(score.getGrandPrixName(), score.getParticipantName(), score.getParticipantSurname(), score.getPointsSum(), numberOfJokersUsed);
            grandPrixScoreWithJokersList.add(grandPrixScoreWithJokers);
        }

        return grandPrixScoreWithJokersList;
    }
}
