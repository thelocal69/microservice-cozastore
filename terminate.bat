echo off

:: Terminate processes running on specified ports
for /f "tokens=5" %%p in ('netstat -a -n -o ^| findstr :8761 :8085 :8080 :8084 :8082 :8086 :8089 :8081 :8083') do taskkill /f /pid %%p

:: Wait for all processes to finish
timeout /t 5