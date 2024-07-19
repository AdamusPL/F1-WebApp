package com.typerf1.typerf1.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class GrandPrixScoreWithJokers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String grandPrixName;
    String participantName;
    String participantSurname;
    Long pointsSum;
    Long numberOfJokersUsed;

    public GrandPrixScoreWithJokers(String grandPrixName, String participantName, String participantSurname, Long pointsSum, Long numberOfJokersUsed) {
        this.grandPrixName = grandPrixName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.pointsSum = pointsSum;
        this.numberOfJokersUsed = numberOfJokersUsed;
    }
}
