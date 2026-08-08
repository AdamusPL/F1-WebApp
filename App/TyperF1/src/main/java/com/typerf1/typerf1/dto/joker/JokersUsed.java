package com.typerf1.typerf1.dto.joker;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JokersUsed {
    int id;
    String participantName;
    String participantSurname;
    Long numberOfJokersUsed;

    public JokersUsed(String participantName, String participantSurname, Long numberOfJokersUsed) {
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.numberOfJokersUsed = numberOfJokersUsed;
    }
}
