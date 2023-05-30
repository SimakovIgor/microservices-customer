package ru.simakov.service;

import org.springframework.stereotype.Service;
import ru.simakov.model.Customer;
import ru.simakov.model.CustomerRegistrationRq;
import ru.simakov.repository.CustomerRepository;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegistrationRq customerRegistrationRq) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRq.getFirstName())
                .firstName(customerRegistrationRq.getLastName())
                .firstName(customerRegistrationRq.getEmail())
                .build();
        customerRepository.save(customer);
    }
}
