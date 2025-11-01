const API_BASE_URL = 'http://localhost:8080/api';

class CATQuestionService {
  /**
   * Generate new CAT questions from the backend
   */
  async generateQuestions() {
    try {
      const response = await fetch(`${API_BASE_URL}/cat-questions/generate`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Failed to generate questions: ${response.status} - ${errorText}`);
      }

      return await response.json();
    } catch (error) {
      console.error('Error generating questions:', error);
      throw error;
    }
  }

  /**
   * Validate user's answer
   */
  async validateAnswer(selectedAnswer, correctAnswer) {
    try {
      const response = await fetch(`${API_BASE_URL}/cat-questions/validate-answer`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          selectedAnswer,
          correctAnswer,
          detailedReasoning: '',
        }),
      });

      if (!response.ok) {
        throw new Error(`Failed to validate answer: ${response.status}`);
      }

      return await response.json();
    } catch (error) {
      console.error('Error validating answer:', error);
      throw error;
    }
  }

  /**
   * Check service health
   */
  async checkHealth() {
    try {
      const response = await fetch(`${API_BASE_URL}/cat-questions/health`);
      return await response.text();
    } catch (error) {
      console.error('Error checking health:', error);
      throw error;
    }
  }
}

// Create and export a single instance
const catQuestionService = new CATQuestionService();
export default catQuestionService;