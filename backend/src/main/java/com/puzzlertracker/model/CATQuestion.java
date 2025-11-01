package com.puzzlertracker.model;

import java.util.List;

/**
 * DTO for CAT question response from LLM
 * Matches the structured output schema for Gemini API
 */
public class CATQuestion {
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    
    // Constructors
    public CATQuestion() {}
    
    public CATQuestion(String questionText, List<String> options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
    
    // Getters and Setters
    public String getQuestionText() {
        return questionText;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public List<String> getOptions() {
        return options;
    }
    
    public void setOptions(List<String> options) {
        this.options = options;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}