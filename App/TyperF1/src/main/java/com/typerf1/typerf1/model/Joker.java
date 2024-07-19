package com.typerf1.typerf1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Joker {
    @jakarta.persistence.Id
    Integer id;

    @ManyToOne
    @JoinColumn(name="ParticipantId")
    Participant participant;

    @OneToOne
    @JoinColumn(name="GrandPrixId")
    GrandPrix grandPrix;

}
