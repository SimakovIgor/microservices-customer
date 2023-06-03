package ru.simakov.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "ru.simakov.clients")
@Configuration
public class OpenFeignConfig {
}
