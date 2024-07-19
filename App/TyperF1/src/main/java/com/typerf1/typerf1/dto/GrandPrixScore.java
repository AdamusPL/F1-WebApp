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
public class GrandPrixScore {
    @Id
    int grandPrixId;
    String sessionName;
    String participantName;
    String participantSurname;
    Long pointsSum;

    public GrandPrixScore(int grandPrixId, String participantName, String participantSurname, Long pointsSum) {
        this.grandPrixId = grandPrixId;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.pointsSum = pointsSum;
    }
}
