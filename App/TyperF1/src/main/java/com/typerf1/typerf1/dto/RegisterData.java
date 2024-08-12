package com.typerf1.typerf1.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegisterData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String firstName;
    String surname;
    String username;
    String email;
    String password;
    String description;

    public RegisterData(String firstName, String surname, String username, String email){
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.email = email;
    }
}
