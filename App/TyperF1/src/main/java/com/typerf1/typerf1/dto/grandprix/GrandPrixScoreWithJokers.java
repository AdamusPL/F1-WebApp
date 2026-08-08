package com.typerf1.typerf1.dto.grandprix;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GrandPrixScoreWithJokers {
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
