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
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int year;
    String grandPrixName;
    String sessionName;
    String participantName;
    String participantSurname;
    int points;

    public Score(int year, String grandPrixName, String sessionName, String participantName, String participantSurname, int points) {
        this.year = year;
        this.grandPrixName = grandPrixName;
        this.sessionName = sessionName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.points = points;
    }
}
