package com.typerf1.typerf1.dto.joker;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsedJokersGP {
    int id;
    String grandPrixName;
    String participantName;
    String participantSurname;
    Long numberOfJokersUsed;

    public UsedJokersGP(String grandPrixName, String participantName, String participantSurname, Long numberOfJokersUsed) {
        this.grandPrixName = grandPrixName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.numberOfJokersUsed = numberOfJokersUsed;
    }
}
