color 9f
mode con: cols=70 lines=40
@@echo off
echo Running Implementation Script...
IF EXIST "C:\Install\BixbyLogon.bat" (
	echo Script already installed, skipping......
	exit
) ELSE (
	echo Script not yet present, installing...
)
echo   ^> Checking full connectivity to the server...
ping 10.70.50.233 -n 1 -i 255 >nul 2>&1
if %ERRORLEVEL% EQU 0 (
	echo     ^> Connection to the Server successfully...
) Else (
	echo     ^> ERROR: Connection to the Server NOT successfully, exiting...
	goto ende
)
echo   ^> connecting temporary networkdrive...
net use J: /DELETE >nul 2>&1
net use J: \\denu00fsp001\Bixby_Germany /PERSISTENT:YES >nul 2>&1
if %ERRORLEVEL% EQU 0 (
	echo     ^> Success...
) Else (
	echo     ^> ERROR: cannot connect temporary networkdrive, exiting...
	goto ende
)
echo   ^> Adding Registry Key for autostart of script...
REG ADD HKCU\Software\Microsoft\Windows\CurrentVersion\Run /v BixbyLogonScript /t REG_SZ /d "C:\Install\BixbyLogon.bat" /f  >nul 2>&1
if %ERRORLEVEL% EQU 0 (
	echo     ^> Success...
) Else (
	echo     ^> ERROR: while trying to write to Registry, exiting...
	goto ende
)
echo   ^> Writing Scriptfile to local System...
mkdir C:\Install >nul 2>&1
del C:\Install\BixbyLogon.bat >nul 2>&1
COPY "J:\scripts$\BixbyLogon.bat" "C:\Install\BixbyLogon.bat" >nul 2>&1
if %ERRORLEVEL% EQU 0 (
	echo     ^> Success...
) Else (
	echo     ^> ERROR: while writing logonscript to local System, exiting...
	goto ende
)
echo   ^> Running Logonscript...
net use J: /DELETE >nul 2>&1
C:\Install\BixbyLogon.bat



echo EOS: normal
pause
exit

:ende
net use J: /DELETE >nul 2>&1
echo EOS: ende
pause
exit