package com.typerf1.typerf1.dto.plot;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScoresDto {
    @Id
    int id;
    String participantName;
    String participantSurname;
    String sessionName;
    String gpName;
    Double points;
}
