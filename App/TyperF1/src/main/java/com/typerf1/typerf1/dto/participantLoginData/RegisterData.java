package com.typerf1.typerf1.dto.participantLoginData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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

    public RegisterData(String name, String surname, String username, String email){
        this.firstName = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
    }
}
