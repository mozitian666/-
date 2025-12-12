@echo off
echo Starting Course Selection System...
echo The browser will open automatically in 5 seconds...
REM Start a background process to open the URL after a delay
start /b cmd /c "timeout /nobreak /t 5 >nul && start http://localhost:8080"
java -jar ..\src\course-system.jar
pause
