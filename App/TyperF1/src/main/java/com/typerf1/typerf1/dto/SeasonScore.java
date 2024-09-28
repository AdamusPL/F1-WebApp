package com.typerf1.typerf1.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SeasonScore {
    @Id
    int seasonId;
    String participantName;
    String participantSurname;
    Double pointsSum;

    public SeasonScore(int seasonId, String participantName, String participantSurname, Double pointsSum) {
        this.seasonId = seasonId;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.pointsSum = pointsSum;
    }
}
