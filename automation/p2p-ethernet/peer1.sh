#https://technet.microsoft.com/en-us/library/cc749323(v=ws.10).aspx

#In the Windows Firewall with Advanced Security snap-in, click Inbound Rules in the tree, and click New Rule in the Actions Pane.
#Click Custom and click Next.
#Click All programs and click Next.
#For Protocol type, select ICMPv4.
#Click Customize for Internet Control Message Protocol (ICMP) settings.
#Click Echo Request, click OK, and then click Next.
#Under Which local IP address does this rule match? and for Which remote IP address does this rule match click either Any IP address or These IP Addresses. If you click These IP addresses, specify the IP addresses and click Add, then click Next.
#Click Allow the connection, and then click Next.
#Under When does this rule apply?, click the active profile, any or all profiles (Domain, Private, Public) to which you want this rule to apply, and then click Next.
#For Name type a name for this rule and for Description an optional description. Click Finish.
#Repeat steps for ICMPv6, selecting ICMPv6 for Protocol Type instead of ICMPv4.

#netsh advfirewall firewall add rule name="ICMP Allow incoming V4 echo request" protocol=icmpv4:8,any dir=in action=allow profile=all enable=true program=any localAddress=any remoteAddress=any
#netsh advfirewall firewall add rule name="Allow ping" protocol=ICMPv4:8,any dir=in action=allow profile=all enable=true program=any localAddress=any remoteAddress=any
#netsh interface ipv4 set address name=eth static 10.0.0.1
ipconfig
ping 10.0.0.2

