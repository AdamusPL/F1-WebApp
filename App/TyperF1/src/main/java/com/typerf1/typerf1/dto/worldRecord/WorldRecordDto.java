package com.typerf1.typerf1.dto.worldRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WorldRecordDto {
    boolean isJokerUsed;
    boolean isBest;
    List<WorldRecord> worldRecords;
}
