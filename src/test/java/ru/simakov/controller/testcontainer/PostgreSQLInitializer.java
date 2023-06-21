package ru.simakov.controller.testcontainer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgreSQLInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final DockerImageName IMAGE = DockerImageName.parse("postgres:14.6-alpine");
    private static final Network NETWORK = Network.newNetwork();
    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>(IMAGE);

    @Override
    public void initialize(final ConfigurableApplicationContext context) {
        CONTAINER
                .withNetwork(NETWORK)
                .withUrlParam("prepareThreshold", "0")
                .start();

        TestPropertyValues.of(
                "spring.datasource.url=" + CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + CONTAINER.getUsername(),
                "spring.datasource.password=" + CONTAINER.getPassword()
        ).applyTo(context.getEnvironment());
    }
}
