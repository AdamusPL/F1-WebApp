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

    public boolean isLoginAndPasswordCorrect(String username, String password){
        List<ParticipantLoginData> participantLoginData = participantLoginDataRepository.findAll();

        for (ParticipantLoginData data : participantLoginData) {
            if (data.getLogin().equals(username) && data.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

}
