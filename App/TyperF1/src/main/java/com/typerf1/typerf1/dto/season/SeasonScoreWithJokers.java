package com.typerf1.typerf1.dto.season;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SeasonScoreWithJokers {
    int id;
    String participantName;
    String participantSurname;
    Double pointsSum;
    Long numberOfJokersUsed;

    public SeasonScoreWithJokers(int id, String participantName, String participantSurname, Double pointsSum, Long numberOfJokersUsed) {
        this.id = id;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.pointsSum = pointsSum;
        this.numberOfJokersUsed = numberOfJokersUsed;
    }
}
