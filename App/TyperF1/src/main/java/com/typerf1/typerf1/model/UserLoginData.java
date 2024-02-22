package com.typerf1.typerf1.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserLoginData {
    @jakarta.persistence.Id
    Integer id;
    String login;
    String password;
}

