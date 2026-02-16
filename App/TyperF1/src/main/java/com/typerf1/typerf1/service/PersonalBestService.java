package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.points.Record;
import com.typerf1.typerf1.dto.worldRecord.WorldRecord;
import com.typerf1.typerf1.repository.PersonalBestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalBestService {

    private final PersonalBestRepository personalBestRepository;

    @Autowired
    public PersonalBestService(PersonalBestRepository personalBestRepository){
        this.personalBestRepository = personalBestRepository;
    }

    public List<WorldRecord> getPersonalBest(Integer id){
        List<WorldRecord> personalBestList = new ArrayList<>();

        //without joker
        putRecord(personalBestList, true, id, "Race", "The highest number of points gained with prediction on Race");
        putRecord(personalBestList, true, id,"Qualifying", "The highest number of points gained with prediction on Qualifying");
        putRecord(personalBestList, true, id,"Sprint", "The highest number of points gained with prediction on Sprint");
        putRecord(personalBestList, false, id,"Race", "The lowest number of points gained with prediction on Race");
        putRecord(personalBestList, false, id,"Qualifying", "The lowest number of points gained with prediction on Qualifying");
        putRecord(personalBestList, false, id,"Sprint", "The lowest number of points gained with prediction on Sprint");

        return personalBestList;
    }

    void putRecord(List<WorldRecord> recordList, boolean highest, Integer id, String find, String key) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Record> toFind;
        if (highest) {
            toFind = personalBestRepository.findHighest(find, id, pageable);
        } else {
            toFind = personalBestRepository.findLowest(find, id, pageable);
        }
        if (!toFind.isEmpty()) {
            var worldRecord = new WorldRecord();
            worldRecord.setName(key);
            worldRecord.setRecord(toFind.getFirst());
            recordList.add(worldRecord);
        }
    }

//    void putRecordJoker(Map<String, Record> recordList, boolean highest, String find, String key) {
//        Pageable pageable = PageRequest.of(0, 1);
//        List<Record> toFind;
//        if (highest) {
//            toFind = personalBestRepository.findHighestJoker(find, pageable);
//        } else {
//            toFind = personalBestRepository.findLowestJoker(find, pageable);
//        }
//        if (!toFind.isEmpty()) {
//            recordList.put(key, toFind.get(0));
//        }
//    }
//
//    void putRecordWeekend(Map<String, Record> recordList, boolean highest, String key) {
//        Pageable pageable = PageRequest.of(0, 1);
//        List<Object[]> results;
//
//        if (highest) {
//            results = personalBestRepository.findHighestWeekend(pageable);
//        } else {
//            results = personalBestRepository.findLowestWeekend(pageable);
//        }
//        if (!results.isEmpty()) {
//            Object[] result = results.get(0);
//            String name = (String) result[0];
//            String surname = (String) result[1];
//            String grandPrixName = (String) result[2];
//            Integer year = (Integer) result[3];
//            Integer pointsSum = ((Number) result[4]).intValue();
//            Record record = new Record(name, surname, grandPrixName, year, pointsSum);
//            recordList.put(key, record);
//        }
//    }
//
//    void putRecordSprintWeekendJoker(Map<String, Record> recordList, boolean highest, String key) {
//        Pageable pageable = PageRequest.of(0, 1);
//        List<Object[]> results;
//
//        if (highest) {
//            results = worldRecordRepository.findHighestSprintWeekendJoker(pageable);
//        } else {
//            results = worldRecordRepository.findLowestSprintWeekendJoker(pageable);
//        }
//        if (!results.isEmpty()) {
//            Object[] result = results.get(0);
//            String name = (String) result[0];
//            String surname = (String) result[1];
//            String grandPrixName = (String) result[2];
//            Integer year = (Integer) result[3];
//            Integer pointsSum = ((Number) result[4]).intValue();
//            Record record = new Record(name, surname, grandPrixName, year, pointsSum);
//            recordList.put(key, record);
//        }
//    }

}
