package com.typerf1.typerf1.dto.points;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Record {
    String participantName;
    String participantSurname;
    String grandPrixName;
    int year;
    double points;

    public Record(String participantName, String participantSurname, String grandPrixName, int year, double points) {
        this.year = year;
        this.grandPrixName = grandPrixName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.points = points;
    }
}
