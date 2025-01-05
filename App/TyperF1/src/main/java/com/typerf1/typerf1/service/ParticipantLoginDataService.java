package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.FullName;
import com.typerf1.typerf1.model.ParticipantLoginData;
import com.typerf1.typerf1.repository.ParticipantLoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantLoginDataService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final ParticipantLoginDataRepository participantLoginDataRepository;

    @Autowired
    public ParticipantLoginDataService(ParticipantLoginDataRepository participantLoginDataRepository) {
        this.participantLoginDataRepository = participantLoginDataRepository;
    }

    public ResponseEntity<String> isLoginAndPasswordCorrect(ParticipantLoginData participantLoginData){
        List<ParticipantLoginData> participantLoginDataList = participantLoginDataRepository.findAll();

        for (ParticipantLoginData data : participantLoginDataList) {
            if (data.getUsername().equals(participantLoginData.getUsername()) && passwordEncoder.matches(data.getPassword(), participantLoginData.getPassword())) {
                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    public String getFullName(String username){
        FullName fullNameObject = participantLoginDataRepository.getFullName(username).get(0);
        return fullNameObject.getFirstName() + ' ' + fullNameObject.getSurname();
    }

}
