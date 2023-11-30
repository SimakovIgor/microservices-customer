package ru.simakov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.simakov.clients.notification.NotificationRequest;
import ru.simakov.config.RabbitMQProperties;
import ru.simakov.starter.amqp.config.RabbitMQMessageProducer;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqProducer {
    private final RabbitMQProperties rabbitMqProperties;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void publishCustomerNotification(final NotificationRequest notificationRequest) {
        rabbitMQMessageProducer.publish(notificationRequest, rabbitMqProperties.getInternalExchange(), "#");
    }
}
