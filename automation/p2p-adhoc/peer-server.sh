#get hostname
hostname

#create hotspot
netsh wlan set hostednetwork mode=allow ssid=fab-hotspot key=00000000

#start hotspot
netsh wlan start hostednetwork

#get ipaddress
ipconfig

