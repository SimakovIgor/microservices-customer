package ru.simakov.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.simakov.client.FraudClient;

@Configuration
public class FraudClientConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public FraudClient fraudClient(RestTemplate restTemplate) {
        return new FraudClient(restTemplate);
    }
}
