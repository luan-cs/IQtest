@echo off
SET BUILD_MODE=debug

if "%1"=="release" (
	SET BUILD_MODE=release
)
SET PROJ_DIR=%CD%


SET BIN_PATH=%PROJ_DIR%\bin
SET BIN_CLASS_PATH=%BIN_PATH%\classes
SET RES_PATH=%PROJ_DIR%\res

SET TOOL_DIR=%PROJ_DIR%\tools
SET ANDROID_HOME=%TOOL_DIR%\android-sdk
SET ANDROID_TOOL_PATH=%ANDROID_HOME%\tools
SET ANDROID_TOOL=%ANDROID_TOOL_PATH%\android
SET ANT_TOOL=%TOOL_DIR%\ant\bin\ant
if exist %BIN_PATH% (
	rd /s/q %BIN_PATH%
)
md %BIN_PATH%

Rem Choose kind of build (full or optimized size application)

:build_ant
echo Building ant.........................................................
call make_ant.bat

