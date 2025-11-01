@echo off
echo ========================================
echo    Puzzler Tracker - Backend Server
echo ========================================
echo.

REM Check if API key is set
if "%GEMINI_API_KEY%"=="" (
    echo ❌ ERROR: GEMINI_API_KEY environment variable is not set!
    echo.
    echo 📋 TO SET YOUR API KEY:
    echo.
    echo Option 1 - Quick Setup (This Session Only):
    echo   set GEMINI_API_KEY=your_api_key_here
    echo   then run this script again
    echo.
    echo Option 2 - Permanent Setup:
    echo   1. Press Win+R, type 'sysdm.cpl', press Enter
    echo   2. Click 'Environment Variables'
    echo   3. Under 'User variables' click 'New'
    echo   4. Variable name: GEMINI_API_KEY
    echo   5. Variable value: your_api_key_here
    echo   6. Restart this command prompt
    echo.
    echo 🔗 Get API Key: https://makersuite.google.com/app/apikey
    echo.
    pause
    exit /b 1
)

echo ✅ Gemini API Key is configured
echo.

REM Navigate to backend directory
cd /d backend

REM Check if Maven wrapper exists
if not exist "mvnw.cmd" (
    echo ⚠️  Maven wrapper not found. Using system Maven...
    
    REM Check if system Maven is available
    mvn --version >nul 2>&1
    if errorlevel 1 (
        echo ❌ Maven not found in system PATH
        echo.
        echo Please install Maven or generate wrapper:
        echo 1. Install Maven: https://maven.apache.org/download.cgi
        echo 2. Add to PATH: Add Maven/bin to your system PATH
        echo 3. Or run: mvn wrapper:wrapper
        echo.
        pause
        exit /b 1
    )
    
    echo � Compiling project with system Maven...
    mvn clean compile
    if errorlevel 1 (
        echo ❌ Compilation failed!
        pause
        exit /b 1
    )
    echo ✅ Compilation successful
    echo.
    echo �🚀 Starting Spring Boot Backend Server with system Maven...
    mvn spring-boot:run
) else (
    echo 🔨 Compiling project with Maven wrapper...
    mvnw clean compile
    if errorlevel 1 (
        echo ❌ Compilation failed!
        pause
        exit /b 1
    )
    echo ✅ Compilation successful
    echo.
    echo 🚀 Starting Spring Boot Backend Server with Maven wrapper...
    mvnw spring-boot:run
)

echo.
echo Backend server should be running at: http://localhost:8080
echo Health check: http://localhost:8080/api/cat-questions/health
echo.
pause