package com.typerf1.typerf1.dto.joker;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UsedJokersGP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
