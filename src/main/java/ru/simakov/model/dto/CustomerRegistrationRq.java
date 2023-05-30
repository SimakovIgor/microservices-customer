package ru.simakov.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRegistrationRq {
    private String firstName;
    private String lastName;
    private String email;
}
