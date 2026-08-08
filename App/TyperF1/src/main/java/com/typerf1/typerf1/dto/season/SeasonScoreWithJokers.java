package com.typerf1.typerf1.dto.season;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
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
