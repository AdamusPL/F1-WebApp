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
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String surname;
    String description;

    @Column(columnDefinition = "MEDIUMBLOB")
    String profilePicture;

    public Participant(Integer id, String name, String surname, String description, String profilePicture){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.profilePicture = profilePicture;
    }

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

    @OneToMany(mappedBy = "participant")
    private List<Predictions> predictions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserId", referencedColumnName = "Id")
    ParticipantLoginData participantLoginData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EmailId", referencedColumnName = "Id")
    Email email;

}
