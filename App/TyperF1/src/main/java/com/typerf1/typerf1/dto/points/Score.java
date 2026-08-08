package com.typerf1.typerf1.dto.points;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Score {
    int grandPrixId;
    int year;
    String grandPrixName;
    int sessionId;
    String sessionName;
    String participantName;
    String participantSurname;
    int pointsId;
    double points;
    boolean jokerUsed;

    public Score(int grandPrixId, int year, String grandPrixName, int sessionId, String sessionName, String participantName, String participantSurname, int pointsId, double points) {
        this.grandPrixId = grandPrixId;
        this.year = year;
        this.grandPrixName = grandPrixName;
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.pointsId = pointsId;
        this.points = points;
        this.jokerUsed = false;
    }
}
