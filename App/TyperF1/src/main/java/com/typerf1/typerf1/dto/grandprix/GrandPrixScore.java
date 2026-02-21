package com.typerf1.typerf1.dto.grandprix;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class GrandPrixScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
