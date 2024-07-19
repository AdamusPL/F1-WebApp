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
public class JokersUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
