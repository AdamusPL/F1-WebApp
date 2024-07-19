package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.SeasonScore;
import com.typerf1.typerf1.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;

    @Autowired
    public SeasonService(SeasonRepository seasonRepository){
        this.seasonRepository = seasonRepository;
    }

    public List<SeasonScore> getParticipantStandings(){
        return seasonRepository.getParticipantStandings();
    }
}
