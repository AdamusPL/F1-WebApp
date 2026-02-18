package com.typerf1.typerf1.dto.participant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantPage {
    Integer id;
    String name;
    String description;
    String profilePicture;
}
