package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.Record;
import com.typerf1.typerf1.repository.PersonalBestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonalBestService {

    private final PersonalBestRepository personalBestRepository;

    @Autowired
    public PersonalBestService(PersonalBestRepository personalBestRepository){
        this.personalBestRepository = personalBestRepository;
    }

    public Map<String, Record> getPersonalBest(String firstName, String surname){
        Map<String, Record> personalBestList = new HashMap<>();

        //without joker
        putRecord(personalBestList, true, firstName, surname, "Race", "highest-race");
        putRecord(personalBestList, true, firstName, surname,"Qualifying", "highest-qualifying");
        putRecord(personalBestList, true, firstName, surname,"Sprint", "highest-sprint");
        putRecord(personalBestList, false, firstName, surname,"Race", "lowest-race");
        putRecord(personalBestList, false, firstName, surname,"Qualifying", "lowest-qualifying");
        putRecord(personalBestList, false, firstName, surname,"Sprint", "lowest-sprint");

        return personalBestList;
    }

    void putRecord(Map<String, Record> recordList, boolean highest, String firstName, String surname, String find, String key) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Record> toFind;
        if (highest) {
            toFind = personalBestRepository.findHighest(find, firstName, surname, pageable);
        } else {
            toFind = personalBestRepository.findLowest(find, firstName, surname, pageable);
        }
        if (!toFind.isEmpty()) {
            recordList.put(key, toFind.get(0));
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
