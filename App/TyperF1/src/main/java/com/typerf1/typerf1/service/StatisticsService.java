package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.*;
import com.typerf1.typerf1.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsService(StatisticsRepository statisticsRepository){
        this.statisticsRepository=statisticsRepository;
    }

    public List<ScoreWithJokers> getScores(Integer year){
        List<Score> scoreList = statisticsRepository.findSeasonScores(year);
        List<JokersUsage> usedJokersGPList = statisticsRepository.findJokerUsageInSeasonScores(year);
        List<ScoreWithJokers> scoreWithJokersList = new ArrayList<>();

        for (Score score : scoreList) {
            Long numberOfJokersUsed = 0L;
            for (JokersUsage jokersUsed : usedJokersGPList) {
                if (jokersUsed.getParticipantName().equals(score.getParticipantName()) && jokersUsed.getParticipantSurname().equals(score.getParticipantSurname()) && jokersUsed.getGrandPrixName().equals(score.getGrandPrixName())) {
                    numberOfJokersUsed = 1L;
                }
            }
            ScoreWithJokers scoreWithJokers = new ScoreWithJokers(score.getGrandPrixName(), score.getSessionName(), score.getParticipantName(), score.getParticipantSurname(), score.getPoints(), numberOfJokersUsed);
            scoreWithJokersList.add(scoreWithJokers);
        }

        return scoreWithJokersList;
    }

    public List<GrandPrixScoreWithJokers> getGrandPrixSummaryScores(Integer year, String grandPrixName){
        List<GrandPrixScore> grandPrixScoreList = statisticsRepository.findGrandPrixSummaryScores(year, grandPrixName);
        List<UsedJokersGP> usedJokersGPList = statisticsRepository.findJokersUsedOnGPs(year, grandPrixName);
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
