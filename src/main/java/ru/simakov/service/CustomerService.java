package ru.simakov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simakov.clients.fraud.FraudClient;
import ru.simakov.clients.notification.NotificationRequest;
import ru.simakov.config.RabbitMQProperties;
import ru.simakov.model.dto.CustomerRegistrationRq;
import ru.simakov.model.entity.Customer;
import ru.simakov.repository.CustomerRepository;
import ru.simakov.starter.amqp.config.RabbitMQMessageProducer;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final RabbitMQProperties rabbitMqProperties;

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

        var fraudCheckResponse = fraudClient.getIsFraudster(customer.getId());
        if (Boolean.TRUE.equals(fraudCheckResponse.getIsFraudster())) {
            throw new IllegalStateException("Customer %s is fraudster".formatted(customer.getId()));
        }

        rabbitMQMessageProducer.publish(getNotificationRequest(customer),
            rabbitMqProperties.getInternalExchange(), rabbitMqProperties.getInternalNotificationRoutingKey());
        return customer;
    }
}
