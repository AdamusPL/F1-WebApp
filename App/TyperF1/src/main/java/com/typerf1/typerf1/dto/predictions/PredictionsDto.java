package com.typerf1.typerf1.dto.predictions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PredictionsDto {
    Integer id;
    List<PredictionsOrder> drivers;
    String fastestLap;
    boolean jokerUsed;
    Double points;
}
