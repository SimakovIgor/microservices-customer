package ru.simakov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simakov.clients.fraud.FraudCheckResponse;
import ru.simakov.clients.fraud.FraudClient;
import ru.simakov.model.dto.CustomerRegistrationRq;
import ru.simakov.model.entity.Customer;
import ru.simakov.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;

    public void registerCustomer(CustomerRegistrationRq customerRegistrationRq) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRq.getFirstName())
                .lastName(customerRegistrationRq.getLastName())
                .email(customerRegistrationRq.getEmail())
                .build();
        customerRepository.save(customer);

        var fraudCheckResponse = fraudClient.getIsFraudster(customer.getId());
        if (Boolean.TRUE.equals(fraudCheckResponse.getIsFraudster())) {
            throw new IllegalStateException("Customer %s is fraudster".formatted(customer.getId()));
        }

    }
}
