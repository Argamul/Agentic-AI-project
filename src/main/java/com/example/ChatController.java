package com.example;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatController {

    private final RestTemplate restTemplate;

    public ChatController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(defaultValue = "Tell me a joke") String message) {
        String url = "http://localhost:5000/chat";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> map = new HashMap<>();
        map.put("message", message);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, String>>() {});
        Map<String, String> response = responseEntity.getBody();

        return response != null ? response.get("response") : "Error: No response from Python service.";
    }
}