package ru.simakov.controller;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.simakov.controller.support.IntegrationTestBase;
import ru.simakov.controller.support.TestDataProvider;
import ru.simakov.model.entity.Customer;

import static org.assertj.core.api.Assertions.assertThat;
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
        var fraudCheckResponse = TestDataProvider.prepareFraudCheckResponse().build();
        var customerRegistrationRq = TestDataProvider.prepareCustomerRegistrationRq().build();

        when(fraudClient.getIsFraudster(anyLong()))
                .thenReturn(fraudCheckResponse);

        ResponseEntity<Customer> responseEntity = testRestTemplate.postForEntity("/api/v1/customers",
                customerRegistrationRq, Customer.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(customerRepository.findAll())
                .first()
                .satisfies(fraudCheckHistory -> {
                    assertThat(fraudCheckHistory.getEmail())
                            .isEqualTo("email");
                    assertThat(fraudCheckHistory.getFirstName())
                            .isEqualTo("name");
                    assertThat(fraudCheckHistory.getLastName())
                            .isEqualTo("lastName");
                });

    }

}