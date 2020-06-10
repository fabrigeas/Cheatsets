!sudo apt-get install openssh-client

	sudo apt-get install openssh-server
	sudo cp /etc/ssh/sshd_config /etc/ssh/sshd_config.original
	sudo chmod a-w /etc/ssh/sshd_config.original

check that ssh server is running on this computer
	
	sudo netstat -anp | grep sshd

connect

	//fab is the name of a user registered on the host
	ssh fab@ip.of.host.ie.remote.pc
	ssh fab@dell //if the remote(server hostname -f =>dell)

	//username not required because username is same in both client and server
	ssh ip.of.remote.pc
	ssh dell //(hostname -f on the remote returned dell)

	//disconnect
	exit

login without password => using rsa-key

	//on client
	//copies publik key client -> server.known hosts
	ssh-copy-id username@ip.of.server.pc

scp copy files between computers

	//without ssh login to the remte host
	// cd to your dest
	scp fabrigeas@remote:/path/to/file/file.ext  /path/in/local/computer
	scp jenny@192.168.2.113:C:/Users/feugang/Desktop/install-ssh-win8.txt .
	
	//already ssh logged in to the remote
	scp /path/to/file/file.ext  username@theOtherComputer:/path/to/dest

examples

	ssh fabrigeas@10.0.0.1 pwd
	 ssh fabrigeas@10.0.0.1 (any command)

install ssh on windows

	# https://github.com/PowerShell/Win32-OpenSSH/wiki/Install-Win32-OpenSSH
	# download https://github.com/PowerShell/Win32-OpenSSH/releases/tag/v0.0.4.0
	# Extract contents to C:\Program Files\OpenSSH
	# Start Powershell as Administrator, then run the ff commands from power shell
	cd 'C:\Program Files\OpenSSH'
	# Install sshd and ssh-agent services.
	powershell -executionpolicy bypass -file install-sshd.ps1
	# Setup SSH host keys (this will generate all the 'host' keys that sshd expects when its starts)
	.\ssh-keygen.exe -A
	# Secure SSH host keys (optional)
	Start-Service ssh-agent
	# download psexec from: https://technet.microsoft.com/en-us/sysinternals/pxexec.aspx
	launch cmd.exe as SYSTEM - psexec.exe -i -s cmd.exe
	register host keys in above cmd.exe
	ssh-add ssh_host_dsa_key
	ssh-add ssh_host_rsa_key
	ssh-add ssh_host_ecdsa_key
	ssh-add ssh_host_ed25519_key

	# Open Firewall
	New-NetFirewallRule -Protocol TCP -LocalPort 22 -Direction Inbound -Action Allow -DisplayName SSH
	#If you need key-based authentication, run the following to setup the key-auth package (for Win7 and Server 2008, see here)
	powershell -executionpolicy bypass -file install-sshlsa.ps1 
	shutdown /r /t 0

	# Set sshd in auto-start mode and open up firewall (optional)
	Set-Service sshd -StartupType Automatic
	Set-Service ssh-agent -StartupType Automatic
	Set-Service sshd -StartupType Automatic 
	netsh advfirewall firewall add rule name='SSH Port' dir=in action=allow protocol=TCP localport=22
