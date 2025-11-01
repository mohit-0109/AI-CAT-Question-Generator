@echo off
echo ========================================
echo    Puzzler Tracker - Local Startup
echo ========================================
echo.

REM Check if API key is set
if "%GEMINI_API_KEY%"=="" (
    echo ❌ ERROR: GEMINI_API_KEY environment variable is not set!
    pause
    exit /b 1
)

echo ✅ Gemini API Key is configured
echo.

REM Start backend
echo 🚀 Starting Backend Server...
start "Backend Server" cmd /k "cd /d backend && mvnw spring-boot:run"

REM Wait a moment for backend to start
echo ⏳ Waiting for backend to initialize...
timeout /t 10 /nobreak > nul

REM Start frontend
echo 🎨 Starting Frontend Server...
start "Frontend Server" cmd /k "cd /d frontend && npm start"

echo.
echo ========================================
echo  🎉 Servers are starting up!
echo.
echo  Backend:  http://localhost:8080
echo  Frontend: http://localhost:3000
echo.
echo  The application will open automatically
echo  in your default browser.
echo ========================================
echo.
pause