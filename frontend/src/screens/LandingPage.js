import React, { useState, useEffect } from 'react';
import { getAllPuzzles } from '../services/puzzleService';
import catQuestionService from '../services/catQuestionService';
import './LandingPage.css';

const PuzzleCard = ({ puzzle }) => (
  <div className="puzzle-card">
    <h3 className="card-title">{puzzle.title}</h3>
    <p className="card-description">{puzzle.description}</p>
    <p className="card-difficulty">Difficulty: {puzzle.difficulty}</p>
  </div>
);

const LandingPage = ({ onNavigate }) => {
  const [puzzles, setPuzzles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [backendHealth, setBackendHealth] = useState(null);
  const [generatingQuestion, setGeneratingQuestion] = useState(false);

  useEffect(() => {
    const fetchPuzzles = async () => {
      try {
        const data = await getAllPuzzles();
        setPuzzles(data);
      } catch (err) {
        setError('Failed to fetch puzzles. Is the backend running?');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    const checkBackendHealth = async () => {
      try {
        const health = await catQuestionService.checkHealth();
        setBackendHealth(health);
      } catch (err) {
        setBackendHealth('Backend not available');
        console.error('Backend health check failed:', err);
      }
    };

    fetchPuzzles();
    checkBackendHealth();
  }, []);

  const handleGenerateQuestion = async () => {
    setGeneratingQuestion(true);
    try {
      await new Promise(resolve => setTimeout(resolve, 500)); // Small delay for UX
      onNavigate('cat-questions');
    } catch (err) {
      console.error('Navigation error:', err);
    } finally {
      setGeneratingQuestion(false);
    }
  };

  if (loading) {
    return (
      <div className="centered">
        <p>Loading puzzles...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="centered">
        <p className="error-text">{error}</p>
      </div>
    );
  }

  return (
    <div className="landing-container">
      <div className="landing-header">
        <h1>🏠 Puzzler Tracker Home</h1>
        <p className="landing-subtitle">Your AI-Powered CAT Exam Practice Platform</p>
        
        {/* Backend Status Indicator */}
        <div className="status-indicator">
          <span className={`status-dot ${backendHealth && backendHealth.includes('API Key configured: true') ? 'online' : 'offline'}`}></span>
          <span className="status-text">
            {backendHealth ? 
              (backendHealth.includes('API Key configured: true') ? 'System Ready ✅' : 'Configure API Key ⚠️') : 
              'Checking System... ⏳'
            }
          </span>
        </div>
      </div>

      {/* Main CTA Section */}
      <div className="hero-section">
        <div className="hero-content">
          <h2>🎯 Generate CAT Questions Instantly</h2>
          <p>Get AI-generated Quantitative Aptitude questions tailored for CAT exam preparation</p>
          
          <button 
            className={`hero-cta-button ${generatingQuestion ? 'loading' : ''}`}
            onClick={handleGenerateQuestion}
            disabled={generatingQuestion}
          >
            {generatingQuestion ? (
              <>
                <span className="spinner"></span>
                Generating Question...
              </>
            ) : (
              <>
                🚀 Generate CAT Question Now
              </>
            )}
          </button>
          
          <div className="quick-stats">
            <div className="stat-item">
              <span className="stat-number">∞</span>
              <span className="stat-label">AI Questions</span>
            </div>
            <div className="stat-item">
              <span className="stat-number">3</span>
              <span className="stat-label">Topics</span>
            </div>
            <div className="stat-item">
              <span className="stat-number">10min</span>
              <span className="stat-label">Per Question</span>
            </div>
          </div>
        </div>
      </div>

      {/* Feature Cards */}
      <div className="features-grid">
        <div className="feature-card-small" onClick={() => onNavigate('cat-questions')}>
          <h3>📊 Time & Distance</h3>
          <p>Master speed, time, and distance problems</p>
        </div>
        <div className="feature-card-small" onClick={() => onNavigate('cat-questions')}>
          <h3>📈 Percentages</h3>
          <p>Excel at percentage calculations</p>
        </div>
        <div className="feature-card-small" onClick={() => onNavigate('cat-questions')}>
          <h3>🧮 Averages</h3>
          <p>Perfect your average computations</p>
        </div>
      </div>

      <div className="puzzles-section">
        <h2 className="section-header">Available Puzzles</h2>
        {puzzles.length > 0 ? (
          <div className="puzzles-grid">
            {puzzles.map((puzzle) => (
              <PuzzleCard key={puzzle.id} puzzle={puzzle} />
            ))}
          </div>
        ) : (
          <div className="no-puzzles">
            <p>No puzzles found. Add some puzzles to get started!</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default LandingPage;
