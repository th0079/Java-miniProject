@echo off
setlocal

REM Build all Java sources into bin\
if not exist "bin" mkdir "bin"

echo Compiling Java sources...
javac -encoding UTF-8 -d bin src\*.java
if errorlevel 1 (
    echo Build failed.
    exit /b 1
)

echo Build succeeded. Classes are in bin\
endlocal
