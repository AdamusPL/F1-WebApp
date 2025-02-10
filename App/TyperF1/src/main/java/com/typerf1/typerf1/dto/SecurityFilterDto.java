package com.typerf1.typerf1.dto;

import com.typerf1.typerf1.config.SecurityConstants;
import lombok.Getter;

@Getter
public class SecurityFilterDto {
    private String accessToken;
    private String name = "token";
    private long expires = SecurityConstants.JWT_EXPIRATION_DATE;
    private String tokenType = "Bearer ";

    public SecurityFilterDto(String accessToken){
        this.accessToken = accessToken;
    }
}