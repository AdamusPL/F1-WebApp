package com.typerf1.typerf1.dto.standings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GrandPrixDto {
    String name;
    List<SessionDto> sessionDto;
}
