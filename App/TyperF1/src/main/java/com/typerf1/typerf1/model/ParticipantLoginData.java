package com.typerf1.typerf1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ParticipantLoginData {
    @jakarta.persistence.Id
    Integer id;
    String username;
    String password;

    @OneToOne(mappedBy = "participantLoginData")
    private Participant participant;

}

