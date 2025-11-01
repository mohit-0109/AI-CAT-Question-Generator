const API_URL = 'http://localhost:8080/api/puzzles';

export const getAllPuzzles = async () => {
  try {
    const response = await fetch(API_URL);
    if (!response.ok) {
      // If puzzle service is disabled (404), return empty array
      if (response.status === 404) {
        console.log('Puzzle service is disabled - this is normal for CAT question mode');
        return [];
      }
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.log("Puzzle service not available - using CAT question mode only:", error.message);
    // Return empty array so the frontend doesn't crash
    return [];
  }
};
