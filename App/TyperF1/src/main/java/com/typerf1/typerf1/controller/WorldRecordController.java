package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.Record;
import com.typerf1.typerf1.service.WorldRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class WorldRecordController {
    public final WorldRecordService worldRecordService;

    public WorldRecordController(WorldRecordService worldRecordService){
        this.worldRecordService = worldRecordService;
    }

    @GetMapping("/world-records")
    public String worldrecords() {
        return "world-records";
    }

    @GetMapping("/get-records")
    public @ResponseBody Map<String, Record> getRecords(){
        return worldRecordService.getRecords();
    }
}
