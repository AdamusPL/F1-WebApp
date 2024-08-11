package com.typerf1.typerf1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    Integer id;
    String name;
    String surname;
    String description;

    @OneToMany(mappedBy = "participant")
    private List<Points> points;

    @OneToMany(mappedBy = "participant")
    private List<Joker> jokers;

    @OneToOne
    @JoinColumn(name = "UserId")
    ParticipantLoginData participantLoginData;

}
