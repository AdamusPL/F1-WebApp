package com.typerf1.typerf1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/check-cookie")
    public String checkCookie() {
        return "check-cookie";
    }
}
