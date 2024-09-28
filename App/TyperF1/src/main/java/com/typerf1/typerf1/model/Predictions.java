package com.typerf1.typerf1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Predictions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String driver1;
    String driver2;
    String driver3;
    String driver4;
    String driver5;
    String driver6;
    String driver7;
    String driver8;
    String driver9;
    String driver10;
    String driver11;
    String driver12;
    String driver13;
    String driver14;
    String driver15;
    String driver16;
    String driver17;
    String driver18;
    String driver19;
    String driver20;
    String fastestLap;

    @ManyToOne
    @JoinColumn(name = "GrandPrixId")
    private GrandPrix grandPrix;

    @ManyToOne
    @JoinColumn(name = "SessionId")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "ParticipantId")
    private Participant participant;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PointsId")
    Points points;

}
