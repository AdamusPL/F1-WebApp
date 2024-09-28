package com.typerf1.typerf1.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Points {
    @jakarta.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    Double number;

    public Points(Double number){
        this.number = number;
    }

    @OneToOne
    @JoinColumn(name = "SessionId")
    Session session;

    @ManyToOne
    @JoinColumn(name = "ParticipantId")
    Participant participant;

    @JsonManagedReference
    @OneToOne(mappedBy = "points", fetch = FetchType.EAGER)
    private Predictions predictions;
}
