package com.puzzlertracker.controller;

import com.puzzlertracker.model.AnswerValidationRequest;
import com.puzzlertracker.model.AnswerValidationResponse;
import com.puzzlertracker.model.CATQuestion;
import com.puzzlertracker.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controller for CAT question generation and validation
 */
@RestController
@RequestMapping("/api/cat-questions")
@CrossOrigin(origins = "http://localhost:3000")
public class CATQuestionController {
    
    @Autowired
    private LLMService llmService;
    
    /**
     * Generate new CAT questions using LLM
     */
    @PostMapping("/generate")
    public Mono<ResponseEntity<CATQuestion>> generateQuestions() {
        System.out.println("=== CAT Question Generation Request ===");
        System.out.println("API Key configured: " + llmService.isApiKeyConfigured());
        
        if (!llmService.isApiKeyConfigured()) {
            System.out.println("Error: Gemini API key not configured");
            return Mono.just(ResponseEntity.badRequest()
                    .header("Error", "Gemini API key not configured")
                    .build());
        }
        
        System.out.println("Starting question generation...");
        return llmService.generateCATQuestions()
                .map(catQuestion -> {
                    System.out.println("Successfully generated CAT question");
                    return ResponseEntity.ok(catQuestion);
                })
                .onErrorResume(error -> {
                    System.out.println("Error generating CAT question: " + error.getMessage());
                    error.printStackTrace();
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }
    
    /**
     * Validate user's answer and provide explanation
     */
    @PostMapping("/validate-answer")
    public ResponseEntity<AnswerValidationResponse> validateAnswer(@RequestBody AnswerValidationRequest request) {
        try {
            boolean isCorrect = request.getSelectedAnswer().equals(request.getCorrectAnswer());
            
            String explanation;
            if (isCorrect) {
                explanation = "Correct! " + request.getDetailedReasoning();
            } else {
                explanation = "Incorrect. The correct answer is " + request.getCorrectAnswer() + ". " + request.getDetailedReasoning();
            }
            
            AnswerValidationResponse response = new AnswerValidationResponse(
                    isCorrect,
                    explanation,
                    request.getCorrectAnswer()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new AnswerValidationResponse(false, "Error validating answer", ""));
        }
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("CAT Question service is running. API Key configured: " + 
                llmService.isApiKeyConfigured());
    }
}