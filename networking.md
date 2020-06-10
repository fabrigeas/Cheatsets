CIDR notation
192.168.1.0/24 =>24=network_prefix && 8bit=host_prefix

subnet_mask=the bitmask that when applied by a bitwise AND operation to any IP address in the network, yields the routing_prefix(network_prefix)
ie host_prefix && subnet_mask = network_prefix
eg
192.168.1.0/24 (network_prefix) ==> 255.255.255.0 (subnet_mask) because 24bits=> 255.255.255.0

if(hostA.routing_prefix != hostB.routing_prefix){
	they are on different networks
}

IPv4 address = 32 bits (8.8.8.8=>01234567.8bit.8bit.8bit) 4 octet
IPv6 address = 128bits (16:16:16:16:16:16)

11111111.11111111.11000000.00000000,=> 255.255.192.0 (18bit subnet mask)
//the modern way
192.168.0.0, netmask 255.255.255.0 is written as 192.168.0.0/24
In IPv6, 2001:db8::/32 designates the address 2001:db8:: and its network prefix consisting of the most significant 32 bits.

//prior to CIDR network prefix = msb of ip address, but now
//assign an ip addr to a net interface requires the Ip && netmask


Given address 192.168.5.130
				Binary form 	D						ot-decimal notation
IP address 		11000000.10101000.00000101.10000010 	192.168.5.130
Subnet mask 	11111111.11111111.11111111.00000000 	255.255.255.0
Network prefix 	11000000.10101000.00000101.00000000 	192.168.5.0 (Ip && subnet)
Host part 		00000000.00000000.00000000.10000010 	0.0.0.130 bitwise AND operation of the address and the one's complement of the subnet mask.

Network 			Network (binary) 						Broadcast address
192.168.5.0/26 		11000000.10101000.00000101.00000000 	192.168.5.63
192.168.5.64/26 	11000000.10101000.00000101.01000000 	192.168.5.127
192.168.5.128/26 	11000000.10101000.00000101.10000000 	192.168.5.191
192.168.5.192/26 	11000000.10101000.00000101.11000000 	192.168.5.255


//minicom
{
is used to comminicate with server through serial comminication.
connect the server to pc via serial,
open teminal,
minicom
username
pass

after any change eg, after connecting serial cable to another server,
press enter to refresh the minicom,
no need to re login	
}

//ARP IS 
{
//will delete a ARP table entry.
arp -d ip.address 
sudo arp -d 10.0.0.2 

//is used to set up a new table entry.(add a static ARP entry to local ARP table)
arp  -s  ip.address mac:address 
sudo arp -s 10.0.0.2 00:0c:29:c0:94:bf 

//view the content of the table
arp -a -n 
...
(10.0.0.2) at 00:0c:29:c0:94:bf [ether] PERM on eth1 //PERManent => because addr has been set statically


obtain my public ipaddress
curl ipecho.net/plain ; echo
}

//iperf | measure connection speed between computers
{
	//set ip address of both computers
	//test the reachability of both 'ping'
	//start the server 
	iperf -s
	//on client start the measurement
	iperf -c ip.addr.of.server
}

//ethtool, change eth parameters
{
	//change duplex
	mii-tool -F 100baseTx-HD eth1 
	mii-tool -F 100baseTx-HD eth1 
}

//Routing ping
{
	
	//PING
	1 ping external address (google.com)
	2 the dns server will be queried and will return the address google 74.125.236.34 
	3 packet is created with dest ip=74.125.236.34  and DEST MAC_addr=MAC of default gateway
	4 packet is forwarded to gateway, which then forwards it to google




	/* NETWORK ARCHITECTURE
	 * suppose an architecture: a gateway(router), with 3 interfaces ie
	 * 1 192.168.1.10
	 * 2 192.168.3.10
	 * 2 125.250.60.59
	 * 
	 * host A connected to 192.168.1.10
	 * host B connected to 192.168.3.10
	 * internet connected to 125.250.60.59
	 * 
	 * Host A,B,internet are 3 different networks and cannot communicate,
	 * a route must be added to each host(network) to be able to comminicate with the other
	 * 
	 * HOST: A
	 * route add default gw 192.168.1.10
	 * HOST: B
	 * route add default gw 192.168.3.10
	 **/
	
	//ubuntu
	{
		Destination     Gateway         Genmask         Flags Metric Ref    Use Iface
		0.0.0.0         192.168.2.1     0.0.0.0         UG    100    0        0 eth0
		169.254.0.0     0.0.0.0         255.255.0.0     U     1000   0        0 wlan0
		192.168.2.0     0.0.0.0         255.255.255.0   U     600    0        0 wlan0
		//is equivalent to below
		default         easy.box.local  0.0.0.0         UG    100    0        0 eth0
		link-local      *               255.255.0.0     U     1000   0        0 wlan0
		192.168.2.0     *               255.255.255.0   U     600    0        0 wlan0
		/* Start from the bottom
		 * 1. packets destined to 192.168.2.0(subnet: 255.255.255.0) 
		 * received on interface wlan0
		 * shoud be forwarded to 
		 * packets with address in range 0.0.0.0 (ie:0-255.0-255.0-255.0-255) 
		 * received on eth0
		 * should be forwarded through the 192.168.2.1 *(not forwarded)
		 * 
		 * 2. packets destined to any address 0.0.0.0(ie:default)
		 * with mask 0.0.0.0 (ie:any range 0-255.0-255.0-255.0-255)
		 * received on eth0
		 * should be forwarded to 192.168.2.1 (ie easy.box.local the name of the router)
		 * 
		 * */
		 
		 //adding routes
		 {
			/*on host A (ip: 192.168.1.10) add route to network(host B) 192.168.2.10*/
			route add 0.0.0.0 gw 192.168.2.10 
			route add default gw 192.168.2.10 //equivalent

			/*on host B (ip: 192.168.2.10) add route to network(host A) 192.168.1.10*/
			route add 0.0.0.0 gw 192.168.1.10
			route add default gw 192.168.1.10 //equivalent

			/*on router: add route from 192.168.1.10 to 192.168.2.10 (bidirectional) */
			route add -net 192.168.1.0 netmask 255.255.255.0 gw 192.168.2.10
			route add -net 192.168.2.0 netmask 255.255.255.0 gw 192.168.1.10

			//add route for external network(in gateway)
			route add default gw 125.250.60.59
			route add 0.0.0.0 gw 125.250.60.59
			
			//blacklist a host 
			route add -host ip.add.of.host reject
		}
	}
	//windows	
	{
	
		Network Destination     Netmask         	 Gateway       	 Interface  		Metric
			  0.0.0.0          	0.0.0.0      		192.168.2.1    	 192.168.2.116    	 25
			 10.0.0.0        	255.0.0.0         	On-link          10.0.0.1   	 	276
			 10.0.0.1  			255.255.255.255     On-link          10.0.0.1    		276
	   10.255.255.255  			255.255.255.255     On-link          10.0.0.1    		276
	   /*Start from bottom,
		* any packet destined to 10.255.255.255(with network mask:255.255.255.255)
		* received on interface 10.0.0.1 
		* should not be sent to router (On-link )
		* 
		* 2.
		* packet destined to 10.0.0.1(255.255.255.255) &&
		* packet destined to 10.0.0.0(any range 10.*.*.*) (mask:255.0.0.0)
		* received on interface 10.0.0.1 
		* sould be listened to && NOT SENT TO 192.168.2.1
		* */
		
		//adding routes
		{
			/*on computer 10.0.2.0 add route to 10.0.0.1  ie through 192.168.0.1*/
			route add 10.0.0.1 mask 255.0.0.0 192.168.0.1
			
			/*on computer 10.0.0.1 add route to 10.0.2.1  ie through 192.168.0.1*/
			route add 10.0.2.0 mask 255.0.255.0 192.168.0.1
			route add 10.0.2.0 192.168.0.1
			
		}
	}

}

//windows
{
	//run cmd as administrator
	//list all interfaces ipv4	
	interface ipv show config
	
	//renew /refresh ip
	ipconfig renew eth0

	//dhcp
	netsh interface ipv4 set address name=eth0 source=dhcp
	
	//set static ip
	netsh interface ipv4 set address name=eth0 static ip_address subnet_mask default_gateway
	netsh interface ipv4 set address name=eth0 static 192.168.3.8 255.255.255.0 192.168.3.1

}

ifconfig -a //display all interfaces even desactivated ones
//without -a, interfaces set to down wont be listed.
with -a, desactiated infaces will show but without the UP attr
ifconfig eth0  //display properties of eth0
ifconfig wlan0 down  //shutdown
ifconfig wlan0 up //turn interface wlan0 on
wlan0 192.168.2.300 //set ipaddress of lan

//delete ipaddress sudo 
ip addr flush dev eth0

//disable interface
ifconfig wlan0 down


ip multiplexing // create several ip aliases for a single interface
ifconfig eth0:1 10.0.0.1
ifconfig eth0:2 11.0.0.2 etc

delete ip address from interface
ip addr flush dev wlan0/eth0/...

renew ipaddress
sudo dhclient -r eth0 //-r release
sudo dhclient eth0	//obtains


//list wifi networks
netsh wlan show networks
//connect to a wifi
netsh wlan connect "ssid"
netsh wlan disconnect
netsh wlan delete profile ssid

//CREATE ADHOC NETWORK
{
	//windows
	{
		//start cmd as admin(Win + x)
		// check that hosted network:yes, else update adapters driver driver
		netsh wlan show drivers | grep -i "hosted network"
		
		netsh wlan set hostednetwork mode=allow ssid="fabrigeas@adhoc" key=fabrigeas
		netsh wlan start hostednetwork
		
	}
}

