package ru.simakov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simakov.clients.fraud.FraudClient;
import ru.simakov.clients.notification.NotificationClient;
import ru.simakov.clients.notification.NotificationRequest;
import ru.simakov.model.dto.CustomerRegistrationRq;
import ru.simakov.model.entity.Customer;
import ru.simakov.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    private static Customer mapCustomer(CustomerRegistrationRq customerRegistrationRq) {
        return Customer.builder()
                .firstName(customerRegistrationRq.getFirstName())
                .lastName(customerRegistrationRq.getLastName())
                .email(customerRegistrationRq.getEmail())
                .build();
    }

    private static NotificationRequest getNotificationRequest(Customer customer) {
        return NotificationRequest.builder()
                .message("Welcome, %s %s!!".formatted(customer.getEmail(), customer.getFirstName()))
                .toCustomerId(customer.getId())
                .toCustomerEmail(customer.getEmail())
                .sender("Customer microservice")
                .build();
    }

    public void registerCustomer(CustomerRegistrationRq customerRegistrationRq) {
        var customer = mapCustomer(customerRegistrationRq);
        customerRepository.save(customer);

        var fraudCheckResponse = fraudClient.getIsFraudster(customer.getId());
        if (Boolean.TRUE.equals(fraudCheckResponse.getIsFraudster())) {
            throw new IllegalStateException("Customer %s is fraudster".formatted(customer.getId()));
        }

        notificationClient.sendNotification(getNotificationRequest(customer));
    }
}
