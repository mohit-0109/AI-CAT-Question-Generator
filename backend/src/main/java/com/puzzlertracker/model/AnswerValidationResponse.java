package com.puzzlertracker.model;

/**
 * DTO for answer validation response
 */
public class AnswerValidationResponse {
    private boolean isCorrect;
    private String explanation;
    private String correctAnswer;
    
    // Constructors
    public AnswerValidationResponse() {}
    
    public AnswerValidationResponse(boolean isCorrect, String explanation, String correctAnswer) {
        this.isCorrect = isCorrect;
        this.explanation = explanation;
        this.correctAnswer = correctAnswer;
    }
    
    // Getters and Setters
    public boolean isCorrect() {
        return isCorrect;
    }
    
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}