package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final WebClient webClient;

    public GeminiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String summarize(String text, String accessToken) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of("parts", List.of(
                                    Map.of("text", text)
                            ))
                    )
            );

            Map response = webClient.post()
                    .uri("/models/gemini-pro:generateContent")
                    .header("Authorization", "Bearer " + accessToken)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null) return "No response from Gemini API";

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            if (candidates == null || candidates.isEmpty()) return "No summary generated";

            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            if (content == null) return "Empty content";

            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            if (parts == null || parts.isEmpty()) return "Empty parts";

            return (String) parts.get(0).get("text");

        } catch (Exception e) {
            return "Gemini API error: " + e.getMessage();
        }
    }
}
