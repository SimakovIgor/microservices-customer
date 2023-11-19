package ru.simakov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simakov.model.dto.CustomerRegistrationRq;
import ru.simakov.model.entity.Customer;
import ru.simakov.service.CustomerService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Customer registerCustomer(final @RequestBody CustomerRegistrationRq customerRegistrationRq) {
        log.info("New registerCustomer {}", customerRegistrationRq);
        return customerService.registerCustomer(customerRegistrationRq);
    }

    @GetMapping
    public void health() {
        log.info("health: " + LocalDateTime.now());
    }
}
