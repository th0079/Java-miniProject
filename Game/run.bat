@echo off
setlocal

REM Build automatically if compiled classes are missing.
if not exist "bin\GameMain.class" (
    call "build.bat"
    if errorlevel 1 (
        echo Build failed. Cannot run the game.
        exit /b 1
    )
)

REM Launch the game in a separate process so this script returns immediately.
start "Java-miniProject Game" java -Dfile.encoding=UTF-8 -cp "bin" GameMain %*

echo Game launched.
endlocal
