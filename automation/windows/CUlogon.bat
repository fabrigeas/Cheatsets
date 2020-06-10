color 9f
mode con: cols=70 lines=40
@@echo off

echo Writing Hostname + Username + Timestamp to Log
echo %date%;%time%;%username%;%computername% >> B:\scripts$\log-hostname-username.txt

echo Adding Printer
rundll32 printui.dll,PrintUIEntry /in /n\\himdwspr01\HIMGPR108

echo Set Printer as Default
rundll32 printui.dll,PrintUIEntry /y /n\\himdwspr01\HIMGPR108

echo Mountin Bixby International Drive
net use I: /DELETE
net use I: \\denu00fsp001\Bixby_International /PERSISTENT:YES