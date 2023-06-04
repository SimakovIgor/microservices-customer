package ru.simakov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simakov.model.dto.CustomerRegistrationRq;
import ru.simakov.service.CustomerService;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
@Log4j2
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRq customerRegistrationRq) {
        log.info("New registerCustomer {}", customerRegistrationRq);
        customerService.registerCustomer(customerRegistrationRq);
    }
}
