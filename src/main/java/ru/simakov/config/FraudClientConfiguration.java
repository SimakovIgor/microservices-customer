package ru.simakov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.simakov.client.FraudClient;

@Configuration
public class FraudClientConfiguration {
    @Bean
    public FraudClient fraudClient() {
        return new FraudClient(new RestTemplate());
    }
}
