# https://github.com/PowerShell/Win32-OpenSSH/wiki/Install-Win32-OpenSSH
# download https://github.com/PowerShell/Win32-OpenSSH/releases/tag/v0.0.4.0
# Extract contents to ~ ie "C:\Users\jenny\openssh"
# Start Powershell as Administrator, then run the ff commands from power shell
cd "C:\Users\jenny\openssh"
cd "C:\Users\fabrigeas\openssh"
powershell -executionpolicy bypass -file install-sshd.ps1
New-NetFirewallRule -Protocol TCP -LocalPort 22 -Direction Inbound -Action Allow -DisplayName SSH
powershell -executionpolicy bypass -file install-sshlsa.ps1 
#shutdown /r /t 0
Set-Service sshd -StartupType Automatic
Set-Service ssh-agent -StartupType Automatic
netsh advfirewall firewall add rule name='SSH Port' dir=in action=allow protocol=TCP localport=22

#rsa key to avoid passwords
#copy ~/.ssh/* to ssh client
