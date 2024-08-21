package com.typerf1.typerf1.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@NoArgsConstructor
public class RegisterData {
    String firstName;
    String surname;
    String username;
    String email;
    String password;
    String description;
    MultipartFile profilePicture;

    public RegisterData(String firstName, String surname, String username, String email){
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.email = email;
    }
}
