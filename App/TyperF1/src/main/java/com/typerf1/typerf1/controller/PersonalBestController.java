package com.typerf1.typerf1.controller;

import com.typerf1.typerf1.dto.Record;
import com.typerf1.typerf1.service.PersonalBestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class PersonalBestController {

    private final PersonalBestService personalBestService;

    public PersonalBestController(PersonalBestService personalBestService){
        this.personalBestService = personalBestService;
    }

    @GetMapping("/get-personal-best")
    public @ResponseBody Map<String, Record> getPersonalBest(@RequestParam String firstName, @RequestParam String surname){
        return personalBestService.getPersonalBest(firstName, surname);
    }

}
