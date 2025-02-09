package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.participantLoginData.RegisterData;
import com.typerf1.typerf1.model.Email;
import com.typerf1.typerf1.model.Participant;
import com.typerf1.typerf1.model.ParticipantLoginData;
import com.typerf1.typerf1.repository.ParticipantRepository;
import com.typerf1.typerf1.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class RegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final RegisterRepository registerRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public RegisterService(RegisterRepository registerRepository, ParticipantRepository participantRepository) {
        this.registerRepository = registerRepository;
        this.participantRepository = participantRepository;
    }


    public ResponseEntity<String> checkExistence(RegisterData registerData) throws IOException {
        List<RegisterData> userDataList = registerRepository.getAllUserData();

        for (RegisterData userData : userDataList) {
            //because every participant has to have different full name
            if (userData.getName().equals(registerData.getName()) && userData.getSurname().equals(registerData.getSurname())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Full name is already taken");
            }
            if (userData.getUsername().equals(registerData.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
            }
            if (userData.getEmail().equals(registerData.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken");
            }
        }

        String fileName = StringUtils.cleanPath(registerData.getProfilePicture().getOriginalFilename());
        Participant participant = new Participant(registerData.getName(), registerData.getSurname(), registerData.getDescription(), Base64.getEncoder().encodeToString(registerData.getProfilePicture().getBytes()));

        //if there's no conflict
        ParticipantLoginData participantLoginData = new ParticipantLoginData(registerData.getUsername(), passwordEncoder.encode(registerData.getPassword()));
        Email email = new Email(registerData.getEmail());

        participantLoginData.setParticipant(participant);
        email.setParticipant(participant);
        participant.setParticipantLoginData(participantLoginData);
        participant.setEmail(email);
        participantRepository.save(participant);

        return ResponseEntity.ok().build();

    }
}
