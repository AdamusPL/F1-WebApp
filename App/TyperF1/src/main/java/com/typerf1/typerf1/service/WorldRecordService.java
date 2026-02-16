package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.points.Record;
import com.typerf1.typerf1.dto.worldRecord.WorldRecord;
import com.typerf1.typerf1.repository.WorldRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorldRecordService {
    private final WorldRecordRepository worldRecordRepository;

    @Autowired
    public WorldRecordService(WorldRecordRepository worldRecordRepository) {
        this.worldRecordRepository = worldRecordRepository;
    }

    public List<WorldRecord> getRecords() {
        List<WorldRecord> recordList = new ArrayList<>();

        //without joker
        putRecord(recordList, true, "Race", "The highest number of points gained with prediction on Race");
        putRecord(recordList, true, "Qualifying", "The highest number of points gained with prediction on Qualifying");
        putRecord(recordList, true, "Sprint", "The highest number of points gained with prediction on Sprint");
        putRecord(recordList, false, "Race", "The lowest number of points gained with prediction on Race");
        putRecord(recordList, false, "Qualifying", "The lowest number of points gained with prediction on Qualifying");
        putRecord(recordList, false, "Sprint", "The lowest number of points gained with prediction on Sprint");

        //with joker
        putRecordJoker(recordList, true, "Race", "The highest number of points gained with prediction on Race");
        putRecordJoker(recordList, true, "Qualifying", "The highest number of points gained with prediction on Qualifying");
        putRecordJoker(recordList, false, "Race", "The lowest number of points gained with prediction on Race");
        putRecordJoker(recordList, false, "Qualifying", "The lowest number of points gained with prediction on Qualifying");

        //weekend without joker
        putRecordWeekend(recordList, true, "The highest number of points gained in non-Sprint race weekend");
        putRecordWeekend(recordList, false, "The lowest number of points gained in non-Sprint race weekend");

        //weekend with joker
        putRecordSprintWeekendJoker(recordList, true, "The highest number of points gained in Sprint race weekend");
        putRecordSprintWeekendJoker(recordList, false, "The lowest number of points gained in Sprint race weekend");

        //sprint weekend without joker

        return recordList;
    }

    void putRecord(List<WorldRecord> recordList, boolean highest, String find, String key) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Record> toFind;
        if (highest) {
            toFind = worldRecordRepository.findHighest(find, pageable);
        } else {
            toFind = worldRecordRepository.findLowest(find, pageable);
        }
        if (!toFind.isEmpty()) {
            var worldRecord = new WorldRecord();
            worldRecord.setName(key);
            worldRecord.setRecord(toFind.getFirst());
            recordList.add(worldRecord);
        }
    }

    void putRecordJoker(List<WorldRecord> recordList, boolean highest, String find, String key) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Record> toFind;
        if (highest) {
            toFind = worldRecordRepository.findHighestJoker(find, pageable);
        } else {
            toFind = worldRecordRepository.findLowestJoker(find, pageable);
        }
        if (!toFind.isEmpty()) {
            var worldRecord = new WorldRecord();
            worldRecord.setName(key);
            worldRecord.setRecord(toFind.getFirst());
            recordList.add(worldRecord);
        }
    }

    void putRecordWeekend(List<WorldRecord> recordList, boolean highest, String key) {
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
            var worldRecord = new WorldRecord();
            worldRecord.setName(key);
            Record record = new Record(name, surname, grandPrixName, year, pointsSum);
            worldRecord.setRecord(record);
            recordList.add(worldRecord);
        }
    }

    void putRecordSprintWeekendJoker(List<WorldRecord> recordList, boolean highest, String key) {
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
            var worldRecord = new WorldRecord();
            worldRecord.setName(key);
            Record record = new Record(name, surname, grandPrixName, year, pointsSum);
            worldRecord.setRecord(record);
            recordList.add(worldRecord);
        }
    }
}
