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
public class ScoreWithJokers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String sessionName;
    String grandPrixName;
    String participantName;
    String participantSurname;
    int points;
    Long numberOfJokersUsed;

    public ScoreWithJokers(String grandPrixName, String sessionName, String participantName, String participantSurname, int points, Long numberOfJokersUsed) {
        this.grandPrixName = grandPrixName;
        this.sessionName = sessionName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.points = points;
        this.numberOfJokersUsed = numberOfJokersUsed;
    }
}
