package ru.simakov.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.simakov.repository.CustomerRepository;
import ru.simakov.starter.testing.base.DatabaseAwareTestBase;
import ru.simakov.starter.testing.initializer.PostgreSQLInitializer;
import ru.simakov.starter.testing.initializer.RabbitMQInitializer;

import java.util.Set;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ContextConfiguration(initializers = {
    PostgreSQLInitializer.class,
    RabbitMQInitializer.class
})
@AutoConfigureStubRunner(
    ids = "ru.simakov.microservices:microservices-fraud:+:8081",
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
public abstract class IntegrationTestBase extends DatabaseAwareTestBase {
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected CustomerRepository customerRepository;
    @LocalServerPort
    protected int localPort;

    @Override
    protected String getSchema() {
        return "public";
    }

    @Override
    protected Set<String> getTables() {
        return Set.of("customer");
    }

}
