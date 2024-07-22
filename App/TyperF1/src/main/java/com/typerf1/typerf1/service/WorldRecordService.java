package com.typerf1.typerf1.service;

import com.typerf1.typerf1.dto.Record;
import com.typerf1.typerf1.dto.RecordLong;
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
    public WorldRecordService(WorldRecordRepository worldRecordRepository){
        this.worldRecordRepository = worldRecordRepository;
    }

    public Map<String, Record> getRecords(){
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

        //weekend with joker
        putRecordWeekendJoker(recordList, true, "highest-non-sprint-weekend-joker");
        putRecordWeekendJoker(recordList, false, "lowest-non-sprint-weekend-joker");

        return recordList;
    }

    void putRecord(Map<String, Record> recordList, boolean highest, String find, String key){
        Pageable pageable = PageRequest.of(0,1);
        List<Record> toFind;
        if(highest) {
            toFind = worldRecordRepository.findHighest(find, pageable);
        }
        else{
            toFind = worldRecordRepository.findLowest(find, pageable);
        }
        if(!toFind.isEmpty()){
            recordList.put(key, toFind.get(0));
        }
    }

    void putRecordJoker(Map<String, Record> recordList, boolean highest, String find, String key){
        Pageable pageable = PageRequest.of(0,1);
        List<Record> toFind;
        if(highest) {
            toFind = worldRecordRepository.findHighestJoker(find, pageable);
        }
        else{
            toFind = worldRecordRepository.findLowestJoker(find, pageable);
        }
        if(!toFind.isEmpty()){
            recordList.put(key, toFind.get(0));
        }
    }

    void putRecordWeekendJoker(Map<String, Record> recordList, boolean highest, String key){
        Pageable pageable = PageRequest.of(0,1);
        List<RecordLong> toFind;

        if(highest) {
            toFind = worldRecordRepository.findHighestWeekendJoker(pageable);
        }
        else{
            toFind = worldRecordRepository.findLowestWeekendJoker(pageable);
        }
        if(!toFind.isEmpty()){
            RecordLong recordLong = toFind.get(0);
            Record record = new Record(recordLong.getParticipantName(), recordLong.getParticipantSurname(), recordLong.getGrandPrixName(), recordLong.getYear(), Math.toIntExact(recordLong.getPoints()));
            recordList.put(key, record);
        }
    }

}
