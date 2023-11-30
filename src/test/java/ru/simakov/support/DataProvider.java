package ru.simakov.support;

import lombok.experimental.UtilityClass;
import ru.simakov.model.dto.CustomerRegistrationRq;

@UtilityClass
public class DataProvider {
    public static CustomerRegistrationRq.CustomerRegistrationRqBuilder prepareCustomerRegistrationRq() {
        return CustomerRegistrationRq.builder()
            .email("email")
            .firstName("name")
            .lastName("lastName");
    }

}
