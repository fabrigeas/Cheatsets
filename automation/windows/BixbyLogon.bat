color 9f
mode con: cols=70 lines=40
@@echo off
net use B: /DELETE
net use B: \\denu00fsp001\Bixby_Germany /PERSISTENT:YES
if exist B:\scripts$\logon.bat (
	B:\scripts$\CUlogon.bat
)  Else (
	echo     ^> ERROR: can access the scriptfile, exiting...
	goto ende
)