package ru.simakov.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.simakov.controller.support.DataProvider;
import ru.simakov.controller.support.IntegrationTestBase;
import ru.simakov.model.entity.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerControllerTest extends IntegrationTestBase {
    private RestTemplateBuilder restTemplateBuilder;

    @BeforeEach
    void beforeEach() {
        this.restTemplateBuilder = new RestTemplateBuilder()
            .rootUri("http://localhost:" + localPort);
    }

    @SneakyThrows
    @Test
    void registerCustomer() {
        var testRestTemplate = new TestRestTemplate(restTemplateBuilder);
        var fraudCheckResponse = DataProvider.prepareFraudCheckResponse().build();
        var customerRegistrationRq = DataProvider.prepareCustomerRegistrationRq().build();

        when(fraudClient.getIsFraudster(anyLong()))
            .thenReturn(fraudCheckResponse);

        ResponseEntity<Customer> responseEntity = testRestTemplate.postForEntity("/api/v1/customers",
            customerRegistrationRq, Customer.class);

        assertThat(responseEntity.getStatusCode())
            .isEqualTo(HttpStatus.OK);
        assertThat(customerRepository.findAll())
            .first()
            .extracting(Customer::getEmail, Customer::getFirstName, Customer::getLastName)
            .containsExactly("email", "name", "lastName");
    }

}
