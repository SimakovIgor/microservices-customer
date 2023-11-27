package ru.simakov.controller;

import org.junit.jupiter.api.Test;
import ru.simakov.commons.model.internal.fraud.FraudCheckResponse;
import ru.simakov.controller.support.DataProvider;
import ru.simakov.controller.support.IntegrationTestBase;
import ru.simakov.model.dto.CustomerRegistrationRq;
import ru.simakov.model.entity.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class CustomerControllerTest extends IntegrationTestBase {

    @Test
    void registerCustomer() {
        var customerRegistrationRq = DataProvider.prepareCustomerRegistrationRq().build();
        assertThat(registerCustomer(customerRegistrationRq))
            .isNotNull();

        assertThat(customerRepository.findAll())
            .first()
            .extracting(Customer::getEmail, Customer::getFirstName, Customer::getLastName)
            .containsExactly("email", "name", "lastName");
    }

    private FraudCheckResponse registerCustomer(final CustomerRegistrationRq registrationRq) {
        return webTestClient.post()
            .uri(uriBuilder -> uriBuilder
                .host("localhost")
                .port(localPort)
                .pathSegment("api", "v1", "customers")
                .build())
            .bodyValue(registrationRq)
            .exchange()
            .expectStatus().isOk()
            .expectBody(FraudCheckResponse.class)
            .returnResult()
            .getResponseBody();
    }

}
