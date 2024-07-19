package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.GrandPrixScore;
import com.typerf1.typerf1.dto.Score;
import com.typerf1.typerf1.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsService(StatisticsRepository statisticsRepository){
        this.statisticsRepository=statisticsRepository;
    }

    public List<Score> getScores(Integer year){
        return statisticsRepository.findSeasonScores(year);
    }

    public List<GrandPrixScore> getGrandPrixSummaryScores(Integer year, String grandPrixName){
        return statisticsRepository.findGrandPrixSummaryScores(year, grandPrixName);
    }
}
