package com.puzzlertracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlertracker.model.CATQuestion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for integrating with Gemini LLM API
 */
@Service
public class LLMService {
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final CATQuestionService catQuestionService;
    
    @Value("${app.gemini.api.key:}")
    private String geminiApiKey;
    
    @Value("${app.gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent}")
    private String geminiApiUrl;
    
    public LLMService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper, CATQuestionService catQuestionService) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
        this.catQuestionService = catQuestionService;
    }
    
    /**
     * Generate CAT questions using Gemini LLM API
     * @return CATQuestion object with generated questions
     */
    public Mono<CATQuestion> generateCATQuestions() {
        String prompt = catQuestionService.getCATQuestionPrompt();
        
        Map<String, Object> requestBody = createGeminiRequestBody(prompt);
        
        System.out.println("=== Gemini API Request Debug ===");
        System.out.println("API URL: " + geminiApiUrl);
        System.out.println("API Key configured: " + (geminiApiKey != null && !geminiApiKey.trim().isEmpty()));
        System.out.println("API Key length: " + (geminiApiKey != null ? geminiApiKey.length() : 0));
        try {
            System.out.println("Request Body: " + objectMapper.writeValueAsString(requestBody));
        } catch (Exception e) {
            System.out.println("Could not serialize request body: " + e.getMessage());
        }
        
        return webClient.post()
                .uri(geminiApiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-goog-api-key", geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), clientResponse -> {
                    System.out.println("=== API Error Response ===");
                    System.out.println("Status Code: " + clientResponse.statusCode().value());
                    System.out.println("Status Text: " + clientResponse.statusCode().getReasonPhrase());
                    return clientResponse.bodyToMono(String.class)
                            .doOnNext(errorBody -> {
                                System.out.println("Error Response Body: " + errorBody);
                            })
                            .then(Mono.error(new RuntimeException("API Error: " + clientResponse.statusCode())));
                })
                .bodyToMono(String.class)
                .doOnNext(response -> {
                    System.out.println("=== API Success Response ===");
                    System.out.println("Status: 200 OK");
                    System.out.println("Response Body: " + response);
                })
                .map(this::parseGeminiResponse)
                .onErrorMap(throwable -> {
                    System.out.println("=== Error in generateCATQuestions ===");
                    System.out.println("Error: " + throwable.getMessage());
                    throwable.printStackTrace();
                    return new RuntimeException("Failed to generate CAT questions", throwable);
                });
    }
    
    /**
     * Create request body for Gemini API
     */
    private Map<String, Object> createGeminiRequestBody(String prompt) {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> contents = new HashMap<>();
        Map<String, Object> parts = new HashMap<>();
        
        parts.put("text", prompt);
        contents.put("parts", new Object[]{parts});
        requestBody.put("contents", new Object[]{contents});
        
        // Create the response schema for structured output
        Map<String, Object> responseSchema = createCATQuestionSchema();
        
        // Add generation config for structured JSON output
        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("response_mime_type", "application/json");
        generationConfig.put("response_schema", responseSchema);
        generationConfig.put("temperature", 0.1);
        generationConfig.put("topK", 1);
        generationConfig.put("topP", 1);
        generationConfig.put("maxOutputTokens", 8192);
        requestBody.put("generationConfig", generationConfig);
        
        return requestBody;
    }
    
    /**
     * Create the structured output schema for CAT questions
     */
    private Map<String, Object> createCATQuestionSchema() {
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "OBJECT");
        
        // Define properties
        Map<String, Object> properties = new HashMap<>();
        
        // questionText property
        Map<String, Object> questionText = new HashMap<>();
        questionText.put("type", "STRING");
        questionText.put("description", "The main text of the quantitative aptitude question.");
        properties.put("questionText", questionText);
        
        // options property (array of strings)
        Map<String, Object> options = new HashMap<>();
        options.put("type", "ARRAY");
        Map<String, Object> optionsItems = new HashMap<>();
        optionsItems.put("type", "STRING");
        optionsItems.put("description", "A multiple-choice option for the question.");
        options.put("items", optionsItems);
        options.put("description", "A list of four multiple-choice options for the question.");
        properties.put("options", options);
        
        // correctAnswer property
        Map<String, Object> correctAnswer = new HashMap<>();
        correctAnswer.put("type", "STRING");
        correctAnswer.put("description", "The single correct answer from the provided options.");
        properties.put("correctAnswer", correctAnswer);
        
        schema.put("properties", properties);
        
        // Define required fields
        schema.put("required", new String[]{"questionText", "options", "correctAnswer"});
        
        return schema;
    }
    
    /**
     * Parse Gemini API response and extract JSON content
     * With structured output, the response will be in the exact schema format
     */
    private CATQuestion parseGeminiResponse(String response) {
        try {
            System.out.println("=== Parsing Gemini Response ===");
            JsonNode responseNode = objectMapper.readTree(response);
            
            // Extract the generated text from Gemini response
            JsonNode candidatesNode = responseNode.get("candidates");
            if (candidatesNode != null && candidatesNode.isArray() && candidatesNode.size() > 0) {
                JsonNode contentNode = candidatesNode.get(0).get("content");
                if (contentNode != null) {
                    JsonNode partsNode = contentNode.get("parts");
                    if (partsNode != null && partsNode.isArray() && partsNode.size() > 0) {
                        String generatedText = partsNode.get(0).get("text").asText();
                        System.out.println("Generated Text: " + generatedText);
                        
                        // With structured output, the text should be valid JSON matching our schema
                        CATQuestion result = objectMapper.readValue(generatedText, CATQuestion.class);
                        System.out.println("Successfully parsed CATQuestion with structured output");
                        return result;
                    }
                }
            }
            
            System.out.println("Error: Invalid response format from Gemini API");
            System.out.println("Response structure: " + responseNode.toString());
            throw new RuntimeException("Invalid response format from Gemini API");
            
        } catch (Exception e) {
            System.out.println("Error parsing response: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to parse Gemini API response", e);
        }
    }
    
    /**
     * Check if API key is configured
     */
    public boolean isApiKeyConfigured() {
        return geminiApiKey != null && !geminiApiKey.trim().isEmpty();
    }
}