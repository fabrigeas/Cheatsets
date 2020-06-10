#get hostname
hostname

#check if hotspot in profiles
#if it exists in profiles the it can be joined from cmd
#else must be done manually
netsh wlan show profiles

netsh wlan connect name=fab-hotspot
ipconfig
ping fab

