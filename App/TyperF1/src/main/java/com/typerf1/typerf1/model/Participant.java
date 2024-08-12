package com.typerf1.typerf1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Participant {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String surname;
    String description;

    public Participant(String name, String surname, String description){
        this.name = name;
        this.surname = surname;
        this.description = description;
    }

    @OneToMany(mappedBy = "participant")
    private List<Points> points;

    @OneToMany(mappedBy = "participant")
    private List<Joker> jokers;

    @OneToOne
    @JoinColumn(name = "UserId")
    ParticipantLoginData participantLoginData;

    @OneToOne
    @JoinColumn(name = "EmailId")
    Email email;

}
