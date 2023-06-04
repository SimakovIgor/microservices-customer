package ru.simakov.config;

import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.extension.trace.propagation.JaegerPropagator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtelConfig {
    @Bean
    public TextMapPropagator jaegerPropagator() {
        return JaegerPropagator.getInstance();
    }
}
