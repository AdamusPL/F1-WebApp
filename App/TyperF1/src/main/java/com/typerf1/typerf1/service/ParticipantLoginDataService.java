package com.typerf1.typerf1.service;

import com.typerf1.typerf1.model.ParticipantLoginData;
import com.typerf1.typerf1.repository.ParticipantLoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantLoginDataService {

    private final ParticipantLoginDataRepository participantLoginDataRepository;

    @Autowired
    public ParticipantLoginDataService(ParticipantLoginDataRepository participantLoginDataRepository) {
        this.participantLoginDataRepository = participantLoginDataRepository;
    }

    public boolean isLoginAndPasswordCorrect(ParticipantLoginData participantLoginData){
        List<ParticipantLoginData> participantLoginDataList = participantLoginDataRepository.findAll();

        for (ParticipantLoginData data : participantLoginDataList) {
            if (data.getUsername().equals(participantLoginData.getUsername()) && data.getPassword().equals(participantLoginData.getPassword())) {
                return true;
            }
        }

        return false;
    }

}
