package ru.simakov.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RabbitMQProperties {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
}
