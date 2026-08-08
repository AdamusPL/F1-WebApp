package com.typerf1.typerf1.dto.joker;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JokersUsage {
    int id;
    String grandPrixName;
    String participantName;
    String participantSurname;

    public JokersUsage(String grandPrixName, String participantName, String participantSurname) {
        this.grandPrixName = grandPrixName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
    }
}
