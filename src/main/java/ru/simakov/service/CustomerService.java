package ru.simakov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.simakov.model.Customer;
import ru.simakov.model.CustomerRegistrationRq;

@Service
@Slf4j
public class CustomerService {
    public void registerCustomer(CustomerRegistrationRq customerRegistrationRq) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRq.getFirstName())
                .firstName(customerRegistrationRq.getLastName())
                .firstName(customerRegistrationRq.getEmail())
                .build();
    }
}
