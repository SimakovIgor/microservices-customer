package ru.simakov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simakov.model.dto.CustomerRegistrationRq;
import ru.simakov.model.entity.Customer;
import ru.simakov.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void registerCustomer(CustomerRegistrationRq customerRegistrationRq) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRq.getFirstName())
                .firstName(customerRegistrationRq.getLastName())
                .firstName(customerRegistrationRq.getEmail())
                .build();
        customerRepository.save(customer);
    }
}
