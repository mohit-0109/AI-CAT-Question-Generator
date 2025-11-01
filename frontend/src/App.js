import React, { useState } from 'react';
import LandingPage from './screens/LandingPage';
import CATQuestion from './components/CATQuestion';
import './App.css';

const App = () => {
  const [currentView, setCurrentView] = useState('landing');

  const navigateToView = (view) => {
    setCurrentView(view);
  };

  const renderCurrentView = () => {
    switch (currentView) {
      case 'cat-questions':
        return <CATQuestion />;
      case 'landing':
      default:
        return <LandingPage onNavigate={navigateToView} />;
    }
  };

  return (
    <div className="app-container">
      <nav className="app-navigation">
        <div className="nav-brand">Puzzler Tracker</div>
        <div className="nav-links">
          <button 
            className={`nav-link ${currentView === 'landing' ? 'active' : ''}`}
            onClick={() => navigateToView('landing')}
          >
            Home
          </button>
          <button 
            className={`nav-link ${currentView === 'cat-questions' ? 'active' : ''}`}
            onClick={() => navigateToView('cat-questions')}
          >
            CAT Practice
          </button>
        </div>
      </nav>
      
      <main className="app-main">
        {renderCurrentView()}
      </main>
    </div>
  );
};

export default App;
