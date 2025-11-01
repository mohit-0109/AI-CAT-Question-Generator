import React, { useState } from 'react';
import catQuestionService from '../services/catQuestionService';
import './CATQuestion.css';

const CATQuestion = () => {
  const [currentQuestion, setCurrentQuestion] = useState(null);
  const [selectedAnswer, setSelectedAnswer] = useState('');
  const [showResult, setShowResult] = useState(false);
  const [validationResult, setValidationResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  /**
   * Generate new question
   */
  const generateNewQuestion = async () => {
    setLoading(true);
    setError('');
    setShowResult(false);
    setSelectedAnswer('');
    
    try {
      const questionData = await catQuestionService.generateQuestions();
      setCurrentQuestion(questionData);
    } catch (err) {
      setError('Failed to generate questions. Please check if the Gemini API key is configured.');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  /**
   * Handle answer selection
   */
  const handleAnswerSelect = (option) => {
    if (!showResult) {
      setSelectedAnswer(option);
    }
  };

  /**
   * Submit answer for validation
   */
  const submitAnswer = async () => {
    if (!selectedAnswer || !currentQuestion) return;

    setLoading(true);
    try {
      const result = await catQuestionService.validateAnswer(
        selectedAnswer,
        currentQuestion.correctAnswer
      );
      
      setValidationResult(result);
      setShowResult(true);
    } catch (err) {
      setError('Failed to validate answer');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  /**
   * Reset to start new question
   */
  const startNewQuestion = () => {
    generateNewQuestion();
  };

  return (
    <div className="cat-question-container">
      <div className="cat-question-header">
        <h1>CAT Quantitative Aptitude Practice</h1>
        <button 
          onClick={generateNewQuestion} 
          disabled={loading}
          className="generate-btn"
        >
          {loading ? 'Generating...' : 'Generate New Question'}
        </button>
      </div>

      {error && (
        <div className="error-message">
          <p>{error}</p>
        </div>
      )}

      {currentQuestion && (
        <div className="question-content">
          {/* Question Text */}
          <div className="question-section">
            <h3>Question:</h3>
            <p className="question-text">{currentQuestion.questionText}</p>

            {/* Options */}
            <div className="options-container">
              {currentQuestion.options.map((option, index) => (
                <div 
                  key={index}
                  className={`option ${selectedAnswer === option ? 'selected' : ''} ${
                    showResult ? (
                      option === currentQuestion.correctAnswer 
                        ? 'correct' 
                        : option === selectedAnswer && option !== currentQuestion.correctAnswer
                        ? 'incorrect'
                        : ''
                    ) : ''
                  }`}
                  onClick={() => handleAnswerSelect(option)}
                >
                  <span className="option-letter">{String.fromCharCode(65 + index)}</span>
                  <span className="option-text">{option}</span>
                </div>
              ))}
            </div>

            {/* Action Buttons */}
            <div className="action-buttons">
              {!showResult && (
                <button 
                  onClick={submitAnswer}
                  disabled={!selectedAnswer || loading}
                  className="submit-btn"
                >
                  {loading ? 'Checking...' : 'Submit Answer'}
                </button>
              )}
              
              {showResult && (
                <button onClick={startNewQuestion} className="new-question-btn">
                  Generate New Question
                </button>
              )}
            </div>

            {/* Result Display */}
            {showResult && validationResult && (
              <div className={`result-section ${validationResult.isCorrect ? 'correct-result' : 'incorrect-result'}`}>
                <h4>{validationResult.isCorrect ? '✅ Correct!' : '❌ Incorrect'}</h4>
                <div className="explanation">
                  <p><strong>Correct Answer:</strong> {currentQuestion.correctAnswer}</p>
                </div>
              </div>
            )}
          </div>
        </div>
      )}

      {!currentQuestion && !loading && (
        <div className="welcome-message">
          <p>Click "Generate New Question" to start practicing CAT Quantitative Aptitude questions!</p>
        </div>
      )}
    </div>
  );
};

export default CATQuestion;