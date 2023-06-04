package ru.simakov.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerRegistrationRq {
    private String firstName;
    private String lastName;
    private String email;
}
