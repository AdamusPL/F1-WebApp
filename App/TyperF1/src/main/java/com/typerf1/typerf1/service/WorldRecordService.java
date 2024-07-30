package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.Record;
import com.typerf1.typerf1.repository.WorldRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorldRecordService {
    private final WorldRecordRepository worldRecordRepository;

    @Autowired
    public WorldRecordService(WorldRecordRepository worldRecordRepository) {
        this.worldRecordRepository = worldRecordRepository;
    }

    public Map<String, Record> getRecords() {
        Map<String, Record> recordList = new HashMap<>();

        //without joker
        putRecord(recordList, true, "Race", "highest-race");
        putRecord(recordList, true, "Qualifying", "highest-qualifying");
        putRecord(recordList, true, "Sprint", "highest-sprint");
        putRecord(recordList, false, "Race", "lowest-race");
        putRecord(recordList, false, "Qualifying", "lowest-qualifying");
        putRecord(recordList, false, "Sprint", "lowest-sprint");

        //with joker
        putRecordJoker(recordList, true, "Race", "highest-race-joker");
        putRecordJoker(recordList, true, "Qualifying", "highest-qualifying-joker");
        putRecordJoker(recordList, false, "Race", "lowest-race-joker");
        putRecordJoker(recordList, false, "Qualifying", "lowest-qualifying-joker");

        //weekend without joker
        putRecordWeekend(recordList, true, "highest-weekend");
        putRecordWeekend(recordList, false, "lowest-weekend");

        //weekend with joker
        putRecordSprintWeekendJoker(recordList, true, "highest-sprint-weekend-joker");
        putRecordSprintWeekendJoker(recordList, false, "lowest-sprint-weekend-joker");

        return recordList;
    }

    void putRecord(Map<String, Record> recordList, boolean highest, String find, String key) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Record> toFind;
        if (highest) {
            toFind = worldRecordRepository.findHighest(find, pageable);
        } else {
            toFind = worldRecordRepository.findLowest(find, pageable);
        }
        if (!toFind.isEmpty()) {
            recordList.put(key, toFind.get(0));
        }
    }

    void putRecordJoker(Map<String, Record> recordList, boolean highest, String find, String key) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Record> toFind;
        if (highest) {
            toFind = worldRecordRepository.findHighestJoker(find, pageable);
        } else {
            toFind = worldRecordRepository.findLowestJoker(find, pageable);
        }
        if (!toFind.isEmpty()) {
            recordList.put(key, toFind.get(0));
        }
    }

    void putRecordWeekend(Map<String, Record> recordList, boolean highest, String key) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Object[]> results;

        if (highest) {
            results = worldRecordRepository.findHighestWeekend(pageable);
        } else {
            results = worldRecordRepository.findLowestWeekend(pageable);
        }
        if (!results.isEmpty()) {
            Object[] result = results.get(0);
            String name = (String) result[0];
            String surname = (String) result[1];
            String grandPrixName = (String) result[2];
            Integer year = (Integer) result[3];
            Integer pointsSum = ((Number) result[4]).intValue();
            Record record = new Record(name, surname, grandPrixName, year, pointsSum);
            recordList.put(key, record);
        }
    }

    void putRecordSprintWeekendJoker(Map<String, Record> recordList, boolean highest, String key) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Object[]> results;

        if (highest) {
            results = worldRecordRepository.findHighestSprintWeekendJoker(pageable);
        } else {
            results = worldRecordRepository.findLowestSprintWeekendJoker(pageable);
        }
        if (!results.isEmpty()) {
            Object[] result = results.get(0);
            String name = (String) result[0];
            String surname = (String) result[1];
            String grandPrixName = (String) result[2];
            Integer year = (Integer) result[3];
            Integer pointsSum = ((Number) result[4]).intValue();
            Record record = new Record(name, surname, grandPrixName, year, pointsSum);
            recordList.put(key, record);
        }
    }
}
