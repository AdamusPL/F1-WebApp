package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.participant.BetterFullName;
import com.typerf1.typerf1.dto.participant.ParticipantPage;
import com.typerf1.typerf1.model.Participant;
import com.typerf1.typerf1.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<BetterFullName> getParticipantFullNames() {
        var list =  participantRepository.findAll();

        return list.stream()
                .map(p -> new BetterFullName(p.getId(), p.getName(), p.getSurname()))
                .collect(Collectors.toList());
    }

    public List<ParticipantPage> getParticipantPages() {
        var list = participantRepository.findAll();

        return list.stream()
                .map(p -> new ParticipantPage(p.getId(), p.getName(), p.getDescription(), p.getProfilePicture()))
                .collect(Collectors.toList());
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }
}
