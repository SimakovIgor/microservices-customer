package ru.simakov.controller.support;

import lombok.experimental.UtilityClass;
import ru.simakov.clients.fraud.FraudCheckResponse;
import ru.simakov.model.dto.CustomerRegistrationRq;

@UtilityClass
public class TestDataProvider {
    public static CustomerRegistrationRq.CustomerRegistrationRqBuilder prepareCustomerRegistrationRq() {
        return CustomerRegistrationRq.builder()
                .email("email")
                .firstName("name")
                .lastName("lastName");
    }

    public static FraudCheckResponse.FraudCheckResponseBuilder prepareFraudCheckResponse() {
        return FraudCheckResponse.builder()
                .isFraudster(false);
    }
}
