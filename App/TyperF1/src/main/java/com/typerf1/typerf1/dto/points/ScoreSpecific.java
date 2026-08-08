package com.typerf1.typerf1.dto.points;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScoreSpecific {
    int id;
    String participantName;
    String participantSurname;
    double points;
    boolean jokerUsed;
}
