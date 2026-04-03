package com.typerf1.typerf1.dto.points;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
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
    double points;
    boolean jokerUsed;

    public Score(int year, String grandPrixName, String sessionName, String participantName, String participantSurname, double points) {
        this.year = year;
        this.grandPrixName = grandPrixName;
        this.sessionName = sessionName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.points = points;
        this.jokerUsed = false;
    }
}
