package ru.simakov.controller;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import ru.simakov.clients.fraud.FraudCheckResponse;
import ru.simakov.controller.support.IntegrationTestBase;
import ru.simakov.model.dto.CustomerRegistrationRq;
import ru.simakov.model.entity.Customer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerControllerTest extends IntegrationTestBase {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        restTemplateBuilder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + localPort);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @SneakyThrows
    @Test
    void registerCustomer() {
        var fraudCheckResponse = FraudCheckResponse.builder()
                .isFraudster(false)
                .build();

        when(fraudClient.getIsFraudster(anyLong()))
                .thenReturn(fraudCheckResponse);

        var customerRegistrationRq = CustomerRegistrationRq.builder()
                .email("email")
                .firstName("name")
                .lastName("lastName")
                .build();
        ResponseEntity<Customer> responseEntity = testRestTemplate.postForEntity("/api/v1/customers",
                customerRegistrationRq, Customer.class);

        Assertions.assertThat(responseEntity).isNotNull();
    }
}