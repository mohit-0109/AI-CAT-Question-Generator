@echo off
echo Setting up environment variables for Puzzler Tracker...
echo.

REM Gemini API key
set GEMINI_API_KEY=AIzaSyC-cSw8wwHOiW9Mvz8BSztO6_tG6CUmYeU

echo ✅ GEMINI_API_KEY has been set for this session
echo.
echo Your API key: %GEMINI_API_KEY%
echo.
echo Note: This setting is only valid for the current command prompt session.
echo For permanent setup, add this to your system environment variables.
echo.

REM Start the application
echo Starting Puzzler Tracker...
call start-local.bat