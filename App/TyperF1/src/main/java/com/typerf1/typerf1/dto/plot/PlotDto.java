package com.typerf1.typerf1.dto.plot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class PlotDto {
    String participantName;
    String participantSurname;
    List<String> sessions;
    List<Double> scores;
}
