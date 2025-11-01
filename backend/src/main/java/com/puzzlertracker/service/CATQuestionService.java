package com.puzzlertracker.service;

import org.springframework.stereotype.Service;

/**
 * Service for managing CAT exam question generation using LLM API
 */
@Service
public class CATQuestionService {
    
    private static final String CAT_QUESTION_PROMPT = 
        "You are an expert CAT exam Quantitative Aptitude question generator, specializing in Arithmetic topics. Your task is to create ONE challenging, original question that typically takes a student 5-10 minutes to solve. The question should include clear problem text, exactly four multiple-choice options (A, B, C, D), and the correct answer. Focus exclusively on Arithmetic concepts such as Time, Speed and Distance, Averages, Percentages, Profit and Loss, and Ratios.\n\n" +
        "IMPORTANT: Generate ONLY 1 question. Do not generate multiple questions.\n\n" +
        "Here are examples of the type of questions:\n\n" +
        "---\n" +
        "EXAMPLE 1 (Time, Speed and Distance):\n\n" +
        "questionText:\n" +
        "Train A leaves Station P at 8:00 AM traveling towards Station Q at 60 km/h. Station Q is 360 km away from Station P. Train B leaves Station Q at 9:00 AM traveling towards Station P at 90 km/h. At what time will the two trains meet?\n\n" +
        "options:\n" +
        "A) 11:00 AM\n" +
        "B) 11:30 AM\n" +
        "C) 12:00 PM\n" +
        "D) 12:30 PM\n\n" +
        "correctAnswer: A) 11:00 AM\n\n" +
        "---\n" +
        "EXAMPLE 2 (Percentages):\n\n" +
        "questionText:\n" +
        "In an election between two candidates, A and B, 10% of the voters did not cast their votes. 60 votes were declared invalid. Candidate A secured 47% of the total registered votes and won the election by 332 votes. What is the total number of registered voters?\n\n" +
        "options:\n" +
        "A) 6000\n" +
        "B) 6200\n" +
        "C) 6400\n" +
        "D) 6800\n\n" +
        "correctAnswer: D) 6800\n\n" +
        "---\n" +
        "EXAMPLE 3 (Profit and Loss):\n\n" +
        "questionText:\n" +
        "A shopkeeper marks his goods 40% above the cost price. He gives a discount of 15% on the marked price. Additionally, he uses a faulty weight of 900 grams instead of 1 kg. What is his overall profit percentage?\n\n" +
        "options:\n" +
        "A) 32.67%\n" +
        "B) 33.33%\n" +
        "C) 35.50%\n" +
        "D) 36.25%\n\n" +
        "correctAnswer: B) 33.33%\n\n" +
        "---\n\n" +
        "Now, generate ONE NEW challenging Quantitative Aptitude question focusing on Arithmetic topics (Time Speed Distance, Averages, Percentages, Profit Loss, or Ratios). Make sure the question has:\n" +
        "- Clear, detailed problem text\n" +
        "- Exactly four options labeled A, B, C, D\n" +
        "- One correct answer\n\n" +
        "Remember: Generate ONLY 1 question. The question should be fresh, original, and follow the style and complexity of the examples above.";
    
    /**
     * Get the CAT question generation prompt
     * @return The prompt string for LLM
     */
    public String getCATQuestionPrompt() {
        return CAT_QUESTION_PROMPT;
    }
}