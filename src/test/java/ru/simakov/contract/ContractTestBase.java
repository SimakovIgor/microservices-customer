package ru.simakov.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.simakov.Application;
import ru.simakov.clients.notification.NotificationRequest;
import ru.simakov.service.RabbitMqProducer;
import ru.simakov.starter.testing.initializer.PostgreSQLInitializer;
import ru.simakov.starter.testing.initializer.RabbitMQInitializer;

@ActiveProfiles("test")
@SpringBootTest(
    classes = {
        ContractRabbitTestConfiguration.class,
        Application.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ContextConfiguration(initializers = {
    PostgreSQLInitializer.class,
    RabbitMQInitializer.class
})
@AutoConfigureMessageVerifier
public abstract class ContractTestBase {

    @Autowired
    private RabbitMqProducer rabbitMqProducer;

    public void emitPublishCustomerNotificationEvent() {
        final NotificationRequest notificationRequest = NotificationRequest.builder()
            .sender("contract test")
            .toCustomerId(1L)
            .toCustomerEmail("email.contract@mail.ru")
            .build();
        rabbitMqProducer.publishCustomerNotification(notificationRequest);
    }
}
