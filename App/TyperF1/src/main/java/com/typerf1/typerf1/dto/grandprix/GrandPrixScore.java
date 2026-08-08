package com.typerf1.typerf1.dto.grandprix;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GrandPrixScore {
    String grandPrixName;
    String sessionName;
    String participantName;
    String participantSurname;
    Double pointsSum;

    public GrandPrixScore(String grandPrixName, String participantName, String participantSurname, Double pointsSum) {
        this.grandPrixName = grandPrixName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.pointsSum = pointsSum;
    }
}
