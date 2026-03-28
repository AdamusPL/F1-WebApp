package com.typerf1.typerf1.dto.session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionScore {
    Integer id;
    String sessionName;
    String participantName;
    String participantSurname;
    Double pointsSum;
}
