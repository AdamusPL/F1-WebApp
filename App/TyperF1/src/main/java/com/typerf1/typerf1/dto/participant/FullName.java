package com.typerf1.typerf1.dto.participant;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String firstName;
    String surname;
}
