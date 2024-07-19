package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.JokersUsed;
import com.typerf1.typerf1.dto.SeasonScore;
import com.typerf1.typerf1.dto.SeasonScoreWithJokers;
import com.typerf1.typerf1.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;

    @Autowired
    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    public List<SeasonScoreWithJokers> getParticipantStandings(Integer year) {
        List<SeasonScore> pointsList = seasonRepository.getParticipantStandings(year);
        List<JokersUsed> jokersList = seasonRepository.getParticipantJokers(year);
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
}
