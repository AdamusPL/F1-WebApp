package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.RegisterData;
import com.typerf1.typerf1.model.Email;
import com.typerf1.typerf1.model.Participant;
import com.typerf1.typerf1.model.ParticipantLoginData;
import com.typerf1.typerf1.repository.EmailRepository;
import com.typerf1.typerf1.repository.ParticipantLoginDataRepository;
import com.typerf1.typerf1.repository.ParticipantRepository;
import com.typerf1.typerf1.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    private final RegisterRepository registerRepository;
    private final ParticipantRepository participantRepository;
    private final ParticipantLoginDataRepository participantLoginDataRepository;
    private final EmailRepository emailRepository;

    @Autowired
    public RegisterService(RegisterRepository registerRepository, ParticipantRepository participantRepository, ParticipantLoginDataRepository participantLoginDataRepository, EmailRepository emailRepository) {
        this.registerRepository = registerRepository;
        this.participantRepository = participantRepository;
        this.participantLoginDataRepository = participantLoginDataRepository;
        this.emailRepository = emailRepository;
    }


    public ResponseEntity<String> checkExistence(RegisterData registerData) {
        List<RegisterData> userDataList = registerRepository.getAllUserData();

        for (RegisterData userData : userDataList) {
            //because every participant has to have different full name
            if (userData.getFirstName().equals(registerData.getFirstName()) && userData.getSurname().equals(registerData.getSurname())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            if (userData.getUsername().equals(registerData.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            if (userData.getEmail().equals(registerData.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        //if there's no conflict
        ParticipantLoginData participantLoginData = new ParticipantLoginData(registerData.getUsername(), registerData.getPassword());
        participantLoginDataRepository.save(participantLoginData);

        Email email = new Email(registerData.getEmail());
        emailRepository.save(email);

        Participant participant = new Participant(registerData.getFirstName(), registerData.getSurname(), registerData.getDescription());
        participant.setParticipantLoginData(participantLoginData);
        participant.setEmail(email);
        participantRepository.save(participant);

        return ResponseEntity.ok().build();

    }
}
