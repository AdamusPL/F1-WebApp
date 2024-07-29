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
public class RecordLong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String participantName;
    String participantSurname;
    String grandPrixName;
    Integer year;
    Integer points;

    public RecordLong(String participantName, String participantSurname, String grandPrixName, Integer year, Integer points) {
        this.year = year;
        this.grandPrixName = grandPrixName;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.points = points;
    }
}
