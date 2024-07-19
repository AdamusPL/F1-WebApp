package com.typerf1.typerf1.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class JokersUsed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
