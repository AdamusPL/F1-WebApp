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

    String profilePicture;

    public Participant(String name, String surname, String description, String profilePicture){
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.profilePicture = profilePicture;
    }

    @OneToMany(mappedBy = "participant")
    private List<Points> points;

    @OneToMany(mappedBy = "participant")
    private List<Joker> jokers;

    @OneToOne
    @JoinColumn(name = "UserId", referencedColumnName = "Id")
    ParticipantLoginData participantLoginData;

    @OneToOne
    @JoinColumn(name = "EmailId", referencedColumnName = "Id")
    Email email;

}
