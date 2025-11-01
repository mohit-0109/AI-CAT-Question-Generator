package com.puzzlertracker.model;

/**
 * DTO for answer validation request
 */
public class AnswerValidationRequest {
    private int questionIndex;
    private String selectedAnswer;
    private String correctAnswer;
    private String detailedReasoning;
    
    // Constructors
    public AnswerValidationRequest() {}
    
    public AnswerValidationRequest(int questionIndex, String selectedAnswer, String correctAnswer, String detailedReasoning) {
        this.questionIndex = questionIndex;
        this.selectedAnswer = selectedAnswer;
        this.correctAnswer = correctAnswer;
        this.detailedReasoning = detailedReasoning;
    }
    
    // Getters and Setters
    public int getQuestionIndex() {
        return questionIndex;
    }
    
    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }
    
    public String getSelectedAnswer() {
        return selectedAnswer;
    }
    
    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public String getDetailedReasoning() {
        return detailedReasoning;
    }
    
    public void setDetailedReasoning(String detailedReasoning) {
        this.detailedReasoning = detailedReasoning;
    }
}