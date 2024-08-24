package com.typerf1.typerf1.model;

import jakarta.persistence.*;
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
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String username;
    String password;

    public ParticipantLoginData(String username, String password){
        this.username = username;
        this.password = password;
    }

    @OneToOne(mappedBy = "participantLoginData")
    private Participant participant;

}

