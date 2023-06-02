package ru.simakov.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.simakov.model.dto.FraudCheckResponse;

import java.util.Map;

@RequiredArgsConstructor
public class FraudClient {
    private final RestTemplate restTemplate;

    public FraudCheckResponse getIsFraudster(Long customerId) {
        var headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set("customerId", customerId.toString());

        var requestEntity = new HttpEntity<>(headers);

        var responseEntity = restTemplate.exchange(
                "http://FRAUD-SERVICE/api/v1/fraud-check",
                HttpMethod.GET,
                requestEntity,
                FraudCheckResponse.class);

        return responseEntity.getBody();
    }
}
