package com.typerf1.typerf1.dto.plot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScoresDto {
    int id;
    String participantName;
    String participantSurname;
    String sessionName;
    String gpName;
    Double points;
}
