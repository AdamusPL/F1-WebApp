package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.grandprix.GrandPrixScore;
import com.typerf1.typerf1.dto.grandprix.GrandPrixScoreWithJokers;
import com.typerf1.typerf1.dto.joker.JokersUsage;
import com.typerf1.typerf1.dto.joker.UsedJokersGP;
import com.typerf1.typerf1.dto.points.Score;
import com.typerf1.typerf1.dto.standings.GrandPrixDto;
import com.typerf1.typerf1.dto.standings.SessionDto;
import com.typerf1.typerf1.repository.StandingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedHashMap;

import static java.util.stream.Collectors.*;

@Service
public class StandingsService {
    private final StandingsRepository standingsRepository;

    @Autowired
    public StandingsService(StandingsRepository standingsRepository) {
        this.standingsRepository = standingsRepository;
    }

    private static final List<String> SESSION_ORDER = List.of(
            "Free Practice 1",
            "Free Practice 2",
            "Free Practice 3",
            "Qualifying",
            "Sprint",
            "Race"
    );

    Comparator<SessionDto> sessionComparator = Comparator.comparingInt(s -> {
        int index = SESSION_ORDER.indexOf(s.getName());
        return index == -1 ? Integer.MAX_VALUE : index;
    });

    public List<GrandPrixDto> getScores(Integer year) {
        List<Score> scoreList = standingsRepository.findSeasonScores(year);
        var scores = scoreList.stream()
                .collect(groupingBy(
                        Score::getGrandPrixName,
                        LinkedHashMap::new, // This preserves the DB order for GPs
                        groupingBy(
                                Score::getSessionName,
                                LinkedHashMap::new, // This preserves the DB order for Sessions
                                toList()
                        )
                ))
                .entrySet().stream()
                .map(gpEntry -> {
                    var sessions = gpEntry.getValue().entrySet().stream()
                            .map(sEntry -> new SessionDto(sEntry.getKey(), sEntry.getValue()))
                            .toList(); // No .sorted() needed if DB order is correct

                    return new GrandPrixDto(gpEntry.getKey(), sessions);
                })
                .toList(); // No .sorted() needed here either

        List<JokersUsage> usedJokersGPList = standingsRepository.findJokerUsageInSeasonScores(year);

        for (var weekend : scores) {
            for (var session : weekend.getSessionDto()) {
                for (var score : session.getScores()) {
                    for (JokersUsage jokersUsed : usedJokersGPList) {
                        if (jokersUsed.getParticipantName().equals(score.getParticipantName()) && jokersUsed.getParticipantSurname().equals(score.getParticipantSurname()) && jokersUsed.getGrandPrixName().equals(score.getGrandPrixName())) {
                            score.setJokerUsed(true);
                        }
                    }
                }
            }
        }

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
