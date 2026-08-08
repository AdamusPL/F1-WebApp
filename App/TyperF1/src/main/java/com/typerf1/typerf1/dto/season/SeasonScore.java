package com.typerf1.typerf1.dto.season;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SeasonScore {
    int participantId;
    int seasonId;
    String participantName;
    String participantSurname;
    Double pointsSum;

    public SeasonScore(int participantId, int seasonId, String participantName, String participantSurname, Double pointsSum) {
        this.participantId = participantId;
        this.seasonId = seasonId;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.pointsSum = pointsSum;
    }
}
