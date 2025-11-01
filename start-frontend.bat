@echo off
echo ========================================
echo    Puzzler Tracker - Frontend Server
echo ========================================
echo.

REM Check if Node.js is installed
node --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Node.js is not installed or not in PATH
    echo.
    echo Please install Node.js from: https://nodejs.org/
    echo Recommended version: Node.js 18 LTS
    echo.
    pause
    exit /b 1
)

REM Check if npm is installed
npm --version >nul 2>&1
if errorlevel 1 (
    echo ❌ npm is not installed or not in PATH
    echo.
    echo Please install Node.js which includes npm
    echo.
    pause
    exit /b 1
)

echo ✅ Node.js version: 
node --version
echo ✅ npm version: 
npm --version
echo.

REM Navigate to frontend directory
cd /d frontend

REM Check if package.json exists
if not exist "package.json" (
    echo ❌ package.json not found in frontend directory
    echo Current location: %cd%
    echo.
    echo Creating React app structure...
    cd ..
    npx create-react-app frontend --template minimal
    cd frontend
    echo.
)

REM Clean install if react-scripts is missing
if not exist "node_modules" (
    echo 📦 Installing npm dependencies...
    npm install
    echo.
) else (
    REM Check if react-scripts exists
    if not exist "node_modules\.bin\react-scripts.cmd" (
        echo 🔧 react-scripts missing, performing clean install...
        rmdir /s /q node_modules 2>nul
        del package-lock.json 2>nul
        npm install
        echo.
    )
)

REM Verify react-scripts installation
npm list react-scripts >nul 2>&1
if errorlevel 1 (
    echo 🔧 Installing react-scripts specifically...
    npm install react-scripts@5.0.1 --save
    echo.
)

echo 🎨 Starting React Frontend Server...
echo.
echo Frontend will be available at: http://localhost:3000
echo.
echo Note: Make sure the backend is running on port 8080
echo Backend health: http://localhost:8080/api/cat-questions/health
echo.

REM Start the React development server
npm start

pause