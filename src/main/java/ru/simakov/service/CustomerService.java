package ru.simakov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.simakov.clients.fraud.FraudWebClient;
import ru.simakov.clients.notification.NotificationRequest;
import ru.simakov.commons.model.internal.fraud.FraudCheckResponse;
import ru.simakov.model.dto.CustomerRegistrationRq;
import ru.simakov.model.entity.Customer;
import ru.simakov.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final RabbitMqProducer rabbitMqProducer;
    private final CustomerRepository customerRepository;
    private final FraudWebClient fraudWebClient;

    private static Customer mapCustomer(final CustomerRegistrationRq customerRegistrationRq) {
        return Customer.builder()
            .firstName(customerRegistrationRq.getFirstName())
            .lastName(customerRegistrationRq.getLastName())
            .email(customerRegistrationRq.getEmail())
            .build();
    }

    private static NotificationRequest getNotificationRequest(final Customer customer) {
        return NotificationRequest.builder()
            .message("Welcome, %s %s!!".formatted(customer.getEmail(), customer.getFirstName()))
            .toCustomerId(customer.getId())
            .toCustomerEmail(customer.getEmail())
            .sender("Customer microservice")
            .build();
    }

    public Customer registerCustomer(final CustomerRegistrationRq customerRegistrationRq) {
        var customer = mapCustomer(customerRegistrationRq);
        customerRepository.save(customer);

        final FraudCheckResponse fraudCheckResponse = fraudWebClient.checkFraudRisk(customer.getId());
        if (Boolean.TRUE.equals(fraudCheckResponse.getIsFraud())) {
            throw new IllegalStateException("Customer %s is fraudster".formatted(customer.getId()));
        }

        final NotificationRequest notificationRequest = getNotificationRequest(customer);
        rabbitMqProducer.publishCustomerNotification(notificationRequest);
        return customer;
    }

    public void printByStatus(String status) {
        Page<Customer> byStatus;

        Pageable pageable = Pageable.ofSize(2);
        do {
            byStatus = customerRepository.findAllByStatus(status, pageable);

            for (Customer customer : byStatus.getContent()) {
                System.out.println(customer);
            }

            pageable = byStatus.nextPageable();
        } while (byStatus.hasNext());
    }

}
