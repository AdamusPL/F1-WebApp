package com.typerf1.typerf1.service;

import com.typerf1.typerf1.config.TokenGenerator;
import com.typerf1.typerf1.dto.FullName;
import com.typerf1.typerf1.dto.SecurityFilterDto;
import com.typerf1.typerf1.model.ParticipantLoginData;
import com.typerf1.typerf1.repository.ParticipantLoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantLoginDataService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final ParticipantLoginDataRepository participantLoginDataRepository;
    private TokenGenerator tokenGenerator;

    @Autowired
    public ParticipantLoginDataService(ParticipantLoginDataRepository participantLoginDataRepository,
                                       TokenGenerator tokenGenerator) {
        this.participantLoginDataRepository = participantLoginDataRepository;
        this.tokenGenerator = tokenGenerator;
    }

    public ResponseEntity<SecurityFilterDto> isLoginAndPasswordCorrect(ParticipantLoginData participantLoginData){
        List<ParticipantLoginData> participantLoginDataList = participantLoginDataRepository.findAllByUsername(participantLoginData.getUsername());

        for (ParticipantLoginData data : participantLoginDataList) {
            if (data.getUsername().equals(participantLoginData.getUsername()) && passwordEncoder.matches(participantLoginData.getPassword(), data.getPassword())) {
                String token = tokenGenerator.generateToken(data.getUsername());
                return new ResponseEntity<>(new SecurityFilterDto(token), HttpStatus.OK);
            }
        }

        return ResponseEntity.notFound().build();
    }

    public String getFullName(){
        FullName fullNameObject = participantLoginDataRepository.getFullName(SecurityContextHolder.getContext()
                        .getAuthentication().getName()).get(0);
        return fullNameObject.getFirstName() + ' ' + fullNameObject.getSurname();
    }

}
