color 9f
mode con: cols=70 lines=40
@@echo off

rundll32 printui.dll,PrintUIEntry /in /n\\himdwspr01\HIMGPR108

echo Setting Printer as Default Printer...
rundll32 printui.dll,PrintUIEntry /y /n\\himdwspr01\HIMGPR108

echo Done!
pause
exit