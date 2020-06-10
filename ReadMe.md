# Humans

this repository contains my notes from different subjects.
I perefered a distributted approach so as to be able to acces my data everewhre with no need to transport it.

A good practice is to clone, or pull, then branch and edit.
after editing,checkout the master back and merge, then pull and push.

NB Use geany editor to read the notes files because 
it enables easya wrap and expand of notes that are organized as blocks

## generalal notes

	check if is android sdk installed?? run in cmd

adb

	is java JDK installed?? run in cmd

		java

Protect yourrrself

		2 - factor dentification:
			[gmail](https://myaccount.google.com/security?pli=1#signin)
		Encrypt hard drive: bitlocker in windows

alias in windows

		edit the environmental vars,
		Systems variables
		new
		exit:exit
		restart git bash, $exit

mysql in eclipse

		start service manuelly 
		services.msc
		right click on mysql57 ->start

		"C:\Program Files\MySQL\MySQL Server 5.7\bin\mysqld.exe" --defaults-file="C:\ProgramData\MySQL\MySQL Server 5.7\my.ini" MySQL57

		or net start mysql57
		or 
		sc start mysql57

## RSA

generate in bash

		ssh-keygen -t rsa -b 4096 -C "fabrigeas@gmail.com"
		ssh-add ~/.ssh/id_rsa
		clip < ~/.ssh/id_rsa.pub
	
## Environmental variables

	
	//The following commands are executed in cmd
	//use set for temporary 
	//use setx for permanent
	
	//backup
	PATH > path_to_backup_file
	
	//truncate
	setx path "%path%;C:\Program Files (x86)\Git\bin\"
	SETX PATH "%PATH%;C:\xampp\php
	PATH %PATH%;C:\xampp\php

	//using registry
	reg add "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v Path /t REG_SZ /d "%path%;c:\C:\xampp\php"
	reg query "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v Path

## Add vs code to context menu

	;vs-code-context-menu.reg
	Windows Registry Editor Version 5.00

	[HKEY_CLASSES_ROOT\*\shell\Open with VS Code]
	@="Edit with VS Code"
	"Icon"="C:\\Program Files\\Microsoft VS Code\\Code.exe,0"
	[HKEY_CLASSES_ROOT\*\shell\Open with VS Code\command]
	@="\"C:\\Program Files\\Microsoft VS Code\\Code.exe\" \"%1\""

	; This will make it appear when you right click ON a folder
	; The "Icon" line can be removed if you don't want the icon to appear
	[HKEY_CLASSES_ROOT\Directory\shell\vscode]
	@="Open Folder as VS Code Project"
	"Icon"="\"C:\\Program Files\\Microsoft VS Code\\Code.exe\",0"
	[HKEY_CLASSES_ROOT\Directory\shell\vscode\command]
	@="\"C:\\Program Files\\Microsoft VS Code\\Code.exe\" \"%1\""


	; This will make it appear when you right click INSIDE a folder
	; The "Icon" line can be removed if you don't want the icon to appear

	[HKEY_CLASSES_ROOT\Directory\Background\shell\vscode]
	@="Open Folder as VS Code Project"
	"Icon"="\"C:\\Program Files\\Microsoft VS Code\\Code.exe\",0"
	[HKEY_CLASSES_ROOT\Directory\Background\shell\vscode\command]
	@="\"C:\\Program Files\\Microsoft VS Code\\Code.exe\" \"%V\""

## Windows Wifi commands in cmd

	#list all wireless profiles
	netsh wlan show profiles
	
	#show all wifi profiles for wlan0, iff you have several wireless interfaces
	netsh wlan show profiles interface="wlan0"
	
	#view profile for ssid fabrigeas
	netsh wlan show profile name="fabrigeas"

	#EXPORT|Backup the settings for profile fabrigeas key=clear = password not encoded
	netsh wlan export profile key=clear
	netsh wlan export profile key=clear folder=c:\path\to\save_file\
	netsh wlan export profile name="Profile_Name" key=clear
	netsh wlan export profile name="Profile_Name" folder=.
	netsh wlan export profile name="Profile_Name" key=clear folder=c:\path\to\save_file\
	netsh wlan export profile name="Profile_Name" key=clear folder=c:\path\to\save_file\ interface="wlan03"
	
	#IMPORT WIFI SETTINGS
	Netsh WLAN add profile filename="path\to\file_Path.XML"
	Netsh WLAN add profile filename="path\to\file_Path.XML" Interface="wlan02" user=current
	
	#interfaces and drivers
	Netsh WLAN show drivers
	Netsh WLAN show interfaces
	Netsh WLAN show interface name="wlan10"
		
	#retrive password for a given wifi profile
	Netsh WLAN show profile name="ssid" key=clear
	
	#connect to ssid manually|automatically
	Netsh WLAN set profileparameter name="Profile_Name" connectionmode=manual
	Netsh WLAN set profileparameter name="Profile_Name" connectionmode=auto
	
	#delete a given profile(ssid)
	Netsh WLAN delete profile name="Profile_Name"
	Netsh WLAN delete profile name="jenny"
	
	#CONNECT
	netsh wlan show networks
	
	#list profilies returns jenny
	netsh wlan show profiles 
	
	#reconnect to profile jenny
	netsh wlan connect name=Profile_Name 
	netsh wlan connect name=jenny
	netsh wlan connect name=EasyBox-991707 
	netsh wlan connect ssid=YOURSSID name=jenny 
	netsh wlan connect ssid=jenny 
	netsh wlan connect ssid=YOURSSID name=jenny interface="WIRELESS NETWORK CONNECTION"
	
	#summary
	netsh wlan show profiles 
	netsh wlan connect name=jenny

## Firewall
	
	[#https://support.microsoft.com/en-us/kb/947709](#https://support.microsoft.com/en-us/kb/947709)

	enable ping reply on the computer
	firewall -> advanced settings -> inbound rules -> File and Printer Sharing (Echo Request-ICMPv-In) true

	#enable firewall
	netsh advfirewall set currentprofile state on
	Netsh advfirewall set domainprofile state on 
	netsh advfirewall set domainprofile firewallpolicy blockinbound,allowoutbound
	netsh advfirewall set domainprofile state on 
	netsh advfirewall set privateprofile state on
	
misc

	netsh advfirewall firewall set rule group="remote desktop" new enable=Yes profile=domain 
	netsh advfirewall firewall set rule group="remote desktop" new enable=Yes profile=private
	netsh advfirewall firewall set rule group="remote desktop" new enable=Yes


Create new firewall rules

	netsh advfirewall firewall add rule name="ICMP Allow incoming V4 echo request" protocol=icmpv4:8,any dir=in action=allow
	netsh advfirewall firewall add rule name="All ICMP V4" protocol=icmpv4:any,any dir=in action=allow
	netsh advfirewall firewall add rule name="ICMP Allow incoming V4 echo request" protocol=icmpv4:8,any dir=in action=allow
	netsh advfirewall firewall add rule name="All ICMP V4" protocol=icmpv4:any,any dir=in action=allow
	netsh advfirewall firewall add rule name="My Application" dir=in action=allow program="C:\MyApp\MyApp.exe" enable=yes remoteip=157.60.0.1,172.16.0.0/16,LocalSubnet profile=domain


modify existing rules 'SET' and 'NEW' keywords eg disble ping replying
	
	netsh advfirewall firewall set rule name="File and Printer Sharing (Echo Request - ICMPv4-In)" new enable=no
	netsh advfirewall firewall set rule name="File and Printer Sharing (Echo Request - ICMPv6-In)" new enable=no


#eg enable ping replying

	netsh advfirewall firewall set rule name="File and Printer Sharing (Echo Request - ICMPv4-In)" new enable=yes
	netsh advfirewall firewall set rule name="File and Printer Sharing (Echo Request - ICMPv6-In)" new enable=yes

show startup programs
	
	wmic startup get caption,command

disable a service from autostart

	sc config  serviceName start=disabled
	
List or services

	sc query
	
[Find service by name](https://technet.microsoft.com/en-us/library/bb490907.aspx?f=255&MSPPError=-2147217396)

sc query | Findstr /i "Adob*

Remove login screen

	windows + R
	netplwiz
	uncheck user must ...
	
	mv C://windows/SystemApps/MicrosoftLockApp_cw5... C://windows/SystemApps/MicrosoftLockApp_cw5...bak
	
Rename Users folder

	regedit.Computer\HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\ProfileList\S-1-5-"ProfileImagePath".("C:\Users\old").Data = "C:\Users\new"
	netpzwiz UserToBeEdited.properties.UserName="DesiredUsername"
	restart with temporary account then rename the old C:\Users\old = C:\Users\new 

Search file 
	
	dir /s "filename"

make locahost accessible on LAN

	firewall
	advanced
	add inbound rule
	add the port number of your app
	while true: next

Create a service

	sc.exe create MongoDB binPath= "\"C:\Program Files\MongoDB\Server\3.4\bin\mongod.exe\" --service --config=\"C:\Program Files\MongoDB\Server\3.4\mongod.cfg\"" DisplayName= "MongoDB" start= "auto"

## VIM

	ALL THE FOLLOWING COMMANDS ARE EXECUTED IN EDIT MODE (esc)

	#EXIT
	:
	q /q!

	#EXIT WITHOUT SAVING
	:
	q!

	#DELETE LINE
	dd

	# UNDO
	u 
	:u #one change
	u 	
	c R #
	:red
	. # toggle last

	SELECT
	v+ (MOVE AROUND)

	MOVE AROUND
	[,] #begining,end of fullstop
	{,}	#begining,end of paragraph

Where are passwords stored

	Control Panel\User Accounts\Credential Manager

Clear Cookies, clear session

	F12/applications/Storage/ x

Kill Port already running

	netstat -ano | findstr 3000 
	taskkill/PID 8856 /F
	
Find and rename files After downloading a file tree of android drawable

	find . -iname "*.png" -execdir cp {} "list.png" \;

## Autostart an app

	Windows key
	Locate the app
	right click on the app
	Open file location
	Windows+R
	shell:startup
	paste the logo of your app in the startup folder
