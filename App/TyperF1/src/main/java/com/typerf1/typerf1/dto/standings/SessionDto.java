package com.typerf1.typerf1.dto.standings;

import com.typerf1.typerf1.dto.points.ScoreSpecific;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SessionDto {
    int id;
    String name;
    List<ScoreSpecific> scores;
}
