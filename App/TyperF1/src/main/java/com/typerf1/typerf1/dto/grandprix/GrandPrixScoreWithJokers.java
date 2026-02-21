package com.typerf1.typerf1.dto.grandprix;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
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
    Double pointsSum;
    Long numberOfJokersUsed;

    public GrandPrixScoreWithJokers(String grandPrixName, String participantName, String participantSurname, Double pointsSum, Long numberOfJokersUsed) {
        this.grandPrixName = grandPrixName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.pointsSum = pointsSum;
        this.numberOfJokersUsed = numberOfJokersUsed;
    }
}
