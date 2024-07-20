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
    public WorldRecordService(WorldRecordRepository worldRecordRepository){
        this.worldRecordRepository = worldRecordRepository;
    }

    public Map<String, Record> getRecords(){
        Map<String, Record> recordList = new HashMap<>();

        putRecord(recordList, true, "Race", "highest-race");
        putRecord(recordList, true, "Qualifying", "highest-qualifying");
        putRecord(recordList, true, "Sprint", "highest-sprint");
        putRecord(recordList, false, "Race", "lowest-race");
        putRecord(recordList, false, "Qualifying", "lowest-qualifying");
        putRecord(recordList, false, "Sprint", "lowest-sprint");

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

}
