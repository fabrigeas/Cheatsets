# Unix

## find

```sh
find * -name hello.c
find ~inf2606 # from directory (~inf266)
     -name "filename.ext" # filename
     -type -f -ls # type f =file then ls the result

find ~ -name "*.txt" -exec cat "{}" >> s.out ";" -exec rm -f "{}" ";"
find ~ -name "*.c" -print | xargs fgrep -c find | sort -t: -r -n k2 | head
find  ~fabrigeas/Music/ -iname "*enya.*" -exec cp '{}' ~/Desktop/ \;
find  . -iname "*.txt" -exec egrep -i "keyword" {} +;

find and execute

find ~progbau
-name "filename.ext"
-type f 					# f(file), d(direct)
-exec  head -20 "{}" ";"
-exec  rm -rf -f "{}" ";"
-exec cp -r "{}" . \;# copy -r "{}" what you found, \
```

## tar

```sh
# created
tar -c -f "destNAme.tar" ~progbau/path/to/dir

# extract
tar -x -f unix.tar
tar -xvzf community_images.tar.gz
tar -t -f unix.tar
```

## gzip

```sh
gunzip filename.tar.gz	# returns .tar
gzip filename			# returns.tar.gz
```

## scp between computers

```sh
scp "filename" inf@obelix:.
scp "filename" inf@obelix:.
```

## emacs

```sh

# SAVE
C-x C-s

# open file
C x C f then filename(under)

# exit emacs
C x C c

# begin mark text (select text)
C space then down, up to select the line

# cut
C w
# stop marking
M x

# copy paste
Mark text
M w copy
C y paste

# replace text
M % (escape and %)
then text to replace (return)
then replacement (return)
y each time to confirm

# display line numbers (under)
M x "column-number-mode"

# go to line 17
M x goto-line
enter line number

# open pwd
C x d (ret)

# select Buffer(file) to edit
move curser then Return

# close buffer
C x k

# statusline notification
** ==>unsaved buffer

# special
M x "doctor"
```

## Misc

```bash
# loop in terminal
for i in {1..50};
  do
  echo ""$i
done


# copy and create destination path (-i)
cp -i /folder/ /path/newFolder
cp -i -r folder /path/newDest

# create file tree
mkdir -p /path/path/folder/
mkdir -p /path/path/folder/ && touch /path/path/folder/file.ext


# IMAGE PROCESSING
# convert jpg to pgm
{
  for i in {1..50};
    do
    jpegtopnm 'meJpg($i).jpg' >> 'mePm($i).pgm'
}

# CONVERT COLORED IMAGE TO BLACK AND WHITE
for i in {1..124};
  do
  convert "image($i).jpg" -monochrome dest.jpg
done

-monochrome=gray(true black and white)
-colorspace=> to color
-separate=>
-colorspace Gray

# CONVERT JPG TO PGM
jpegtopnm input.jpg >> output.jpg


# RUN MULTIPLE COMMANDS
A; B    Run A and then B, regardless of success of A
A && B  Run B if A succeeded
A || B  Run B if A failed
A &     Run A in background.

# MORE| less | head | tail
-p | -c => do not scroll, instead clean scree
+3 start at line 3
-7 show 7lines per page
+/fabrice 	# show from fist occurrence of fabrice
head |tail -n 7 # firstlast seven lines
-s sqeeze

# chmod | chown
drrwxrwxrwx=>uuugggooo

-R recurssive
r4, w2, x1

chmod -R 777
  rwx
  a=rwx
  a+rwx
  ugo+rwx
  u+7, g+7, o+7
file

chmod 641| u+rw, g+r, 0+x| file
chmod 7|u+0, g+0, o+7|u-rwx, g-rwx, o+rwx file

chown -R oldUser:newUser file

# compare files
meld fileA fileB

# number of processors
cat /proc/cpuinfo | grep processor | wc -l

cut -f2 file.ext # extract row 2 of file
paste file1 file #merge rows from different files
tr "h2" "3x" < file # replace all h=>3, 2=>x
tr "[a-z]" "[A-Z]" < file # toUppercase
uniq file # remove duplicate lines

```

### Mounting and un mounting

```bash
# MOUNTING (SUDO for each command)

# 1 find usb discs
ls -al /media/username/

# MOUNT USB readonly
sudo mount -o remount,rw /media/username/myUsb/

# list device labels and UUID
blkid

# label returned from bklid
mkdir "/media/label of drive"


# backup the fstab file before editing it
cp /etc/fstab path/to/ackup/fstab.orig

# edit the file
gedit /etc/fstab
UUID=519CB82E5888AD0FOBTAINEDABOVE  "/media/label obtained above"\
  ntfs-3g  defaults,windows_names,locale=en_US.utf8  0 0

mount /media/C
mount -a
mount "/media/label " -o remount,ro,noatime # readonly(ro)

#unmount
umound label

# Summary
ls -al /media/username/
blkid
mkdir "/media/label of drive"
echo "UUID=519CB82E5888AD0FOBTAINEDABOVE  {/media/label obtained above}  ntfs-3g  \
defaults,windows_names,locale=en_US.utf8  0 0" >> /etc/fstab
mount /media/C || mount -a || mount "/media/label " -o remount,ro,noatime # readonly(ro)
```

EXIT SU

su fabrigeas # log back to fabrigeas
su guest

HISTORY

Ctrl R 'string'
history 10 # 10 last commands
1979 ls
1980 ls -l
1981 sudo mount -o remount,rw /media/fabrigeas/17630654159/
1982 hello
1983 cd Notes/
1984 l
1985 git status
1986 history 10
1987 ls
1988 history 10 >> unix.txt
!1981 #=>sudo mount -o remount,rw /media/fabrigeas/17630654159/ =>!sudo
!1979 -al|grep etc # !x returns the exact command as run before with/without params

TERMINAL SHORTCUTS

#prepend ctrl to the ff letters!!
a #line begin
e #end of line
u #delete from start to here
k #del from here to eol
h #erase(backspace)
z #suspend command
w #del(word)
d #del(char) else exit
r #search history

c+s# ctrl+shift+
c #copy
v #paste

store commands in file.sh

chmod +x file.sh
./file

CHANGE COMPUTERS NAME /CHANGE HOSTNAME

sudo nano /etc/hostname /etc/hosts
edit both files

## PS

```bash
# list processes to obtain the PID
ps -ef
ps -f #all processes started by user me (my terminal)

# processes of user fab
ps -f -u fab |
ps -f -u fab,jenny,guest

# processes sorted by cpu usag and memory usage
ps aux --sort=-pcpu,+pmem
ps aux --sort=-pcpu
ps aux --sort=+pmem

# ps as process viewer | process viewer
watch -n 1 'ps -e -o pid,uname,cmd,pmem,pcpu --sort=-pmem,-pcpu | head -15'

# friendly end a process ginven the PID obtained fromps -ef
kill PID | TERM PID | 9 PID

# forcefully kill
kill -KILL PID

# kill using process names
pkill geany

# brutal
pkill -9 geany | pkill _KILL geany

# KILLALL kill all process and its children
killall firefox


# NI | PRIORITY | NICENESS
# start geany with highest priority
nice -19 geany
nice 20 firefox

# alter the NI(nice) value of an already running process
renice 0 geany

ps #list all
ps -ag
ps ax | grep httpd # is apache running??

# kill process
kill  1012
kill 0 #killall except shell
killall httpd

ps # To see currently running process
kill  1012 # To stop any process by PID i.e. to kill process
killall httpd ##kill all apache processes
ps -ag # get information about all running process
kill 0 #To stop all process except your shell

#Run in background
ls / -R | wc -l &
ps aux # display the owner of the processes along with the processes
ps ax | grep  process-U-want-to see #To see if a particular process is running or not
ps ax | grep httpd #see whether Apache web server process is running or not then give command

```

## SORT

```bash
# sort
-n # numeric by default is alphabetic
-r # reversed
-k 5 #  5th column
-f
```

## egrep fgrep | grep (returns lines containing pattern)

```bash

# !ALWAYS USE -E to enable regExp

fgrpp "main" file1 file2 # seach in file1 and file2
fgrep -i "hello  world" main.c # search "hello world " in main (case insensitie)
fgrep -v "" data # search
fgrep -c bash file #
fgrep muster #  terminal waits that user types string from where to search, f

-c # count matches
-i # ignore case
-n # prepend line number
-o #
-v # return lines NOT containing pattern
-H # append fileName too
```

## SED

```bash

sed s/pattern/replacement/ig # i ignore case, g all occurrences
sed s/at/@/ig # i ignore case, g all occurrences
sed 's/man/woman/; s/tiger/wolf/'  #  2 commands, replace man by woman and tiger by wolf
sed "2,131 d" < file #  delete lines 2...131 in file
sed "/<script>/,/<\script>/ d" < file # delete all found within the tag
sed "/^$/d" => delete all empty lines+

# Execute multiple commands
sed -Ee s/man/woman/g -e /Author/d -e /Date/d

# Delete lines containing pattern
sed -e /Date/d -e /Commit/d
sed -E 's;pattern;;'	# delete only first match PER LINE
sed -E 's;pattern;;5'	# delete 5 first match(word not line) /PER LINE
sed -E 's;pattern;;g'	# delete all matches (word not line)
sed -e /commit:/ -/Author/d  -e s/man/woman/g
sed /patter/d
sed s/<[^>]*>#   #delete html tags and content

# Surround numbers &
sed -E 's/([0-9]+)/(&)/g' #  match replaces the &

# Duplicate lines
sed p file # dup all lines
sed 3p file # dup line 3
sed 3, 9p file # dup line 3..9
sed 3, +9p file # dup from line 3, 9 lines

# Extract lines -n
sed -n 3p
sed -n 3, 4p

# edit input file,
sed -i.bak s# / file # create a backup before altering the file
```

## tr translate

```bash
tr -d
tr -d "\n" # delete all lines
tr ab 56 # replace a=5, b=6
tr "\n" "" < file # remove all newline chars
tr a5-7 6C-E #
tr "[:upper:]" "[:lower:]"
tr "[a-z]" "[A-Z]"
```

## UNIQ (delete duplicate lines)

```bash
sort file.ext | uniq -c
```

## RegExp

```bash

meta ^ $ . * + ?  [] () {} | \

# empty lines
^$

[] ^ -

# german alphabet
[a-zäüßö]

# 2-5 digit followed by abc
[1-9]{2,5}abc

# equivalents separated by |

abc{c, } # no effect
a? | a{0, 1}
a+ | a{1, } # a, aa, aaa
a+ | a{0, } # none or any number of occurrences

1234[abc]{3} # 1234 followed by 3 char
[+-]? # optional sign
[+-]?[0-9]+ # integers
<[^]*> # all tags + content
+12355 # match the longest pattern : 1, 12, 123, 1235, 12355
<.*> eg <a href="#top">>asdasd asdasd</a>  # match the whole

# alternative |
ab|cd|ef # ab or cd or ef, returns the first occurrence ie  abcded# ab
[0-9]{3}|[a-z]{4} z9abcd388cc # abcd
{a-z}{4} # any string with 4 letters
grep -E '(fabrice|feugang)' file
egrep  '(fabrice|feugang)' file

# 1-31
[1-9]|[12][0-9]|3[01]
[1-9]|[12][0-9]|3[01]\.[1-9]|1[0-2]\.[0-9]+

# optional
java(script)? # java or javascript
javascript? # javascript or javascript
Da[Da]+	#DaaaDa  # [xy]+ is not repetition [xx or yy] of instead {x or y} infinite number {x, xx, y, yy, xy, yyx, xxy, yxyx ...}
Da(Da) # DaDa, DaDaDa, !{DaaD}
(jean|john)	# {jean, john}
jean (pierre|paul) # {jean pierre, jean paul}
[A-Z][a-z]+(-[A-Z][a-z])? # all names and or surnames

# sub pattern
\1 repeat the last pattern
[a-z]+([0-9]+)b\1 #{ab37b37 # 37, ab37b47 #noMatch => (['"])[^]
(.)\1 # aa, bb, cc
([a-zA-Z]+) +\1 555ba ba555 # ba, !555ba 555ba # no match because the word must be followed by space before r

# Anchors ^ \$
^Worms Hbf$ # lines containing only 'worms Hbf'
^(Dog|Cat)$ # lines Dog or Cat and nothing else

# Back referencing
grep -E '([a-z])\1' # aa, bb, aaa => repeating
sed -E 's/(pattern)\1/(&)/g' # mark the match with \1, reuse it with &

# Match empty lines
/((\r\n|\n|\r)$)|(^(\r\n|\n|\r))|^\s*$

# lines where match occured more than once
awk '{print $0, gsub(/pattern/, "")}' file |
  grep -E -w ([2-9]|1[0-9]+)$ # w or c both correct

# prepend line numbers
awk '{printf "%d %s\n", NR, \$0}' < sed.txt

```

## users

```bash

# add user
sudo adduser feugang

# remove user
sudo userdel username
sudo rm -r /home/username

# user=obsolete
sudo deluser --remove-home obsolete
sudo deluser --remove-all-files obsolete

# To modify the username of a user:
usermod -l new_username old_username

# To change the password for a user:
sudo passwd username

# To change the shell for a user:
sudo chsh username

# To change the details for a user (for example real name):
sudo chfn username
```

## commands

```bash
chown, chgrp, chroot, id: Disambiguating user names and IDs
coreutils: Multi-call program
cat: Concatenate and write files
tac: Concatenate and write files in reverse
nl: Number lines and write files
od: Write files in octal or other formats
base32: Transform data into printable data
base64: Transform data into printable data
fmt: Reformat paragraph text
pr: Paginate or columnate files for printing
fold: Wrap input lines to fit in specified width
head: Output the first part of files
tail: Output the last part of files
split: Split a file into pieces.
csplit: Split a file into context-determined pieces
wc: Print newline, word, and byte counts
sum: Print checksum and block counts
cksum: Print CRC checksum and byte counts
b2sum: Print or check BLAKE2 digests
md5sum: Print or check MD5 digests
sha1sum: Print or check SHA-1 digests
sha2 utilities: Print or check SHA-2 digests
sort: Sort text files
shuf: Shuffling text
uniq: Uniquify files
comm: Compare two sorted files line by line
ptx: Produce permuted indexes
tsort: Topological sort
tsort: Background
cut: Print selected parts of lines
paste: Merge lines of files
join: Join lines on a common field
tr: Translate, squeeze, and/or delete characters
expand: Convert tabs to spaces
unexpand: Convert spaces to tabs
ls: List directory contents
dir: Briefly list directory contents
vdir: Verbosely list directory contents
dircolors: Color setup for ls
cp: Copy files and directories
dd: Convert and copy a file
install: Copy files and set attributes
mv: Move (rename) files
rm: Remove files or directories
shred: Remove files more securely
link: Make a hard link via the link syscall
ln: Make links between files
mkdir: Make directories
mkfifo: Make FIFOs (named pipes)
mknod: Make block or character special files
readlink: Print value of a symlink or canonical file name
rmdir: Remove empty directories
unlink: Remove files via the unlink syscall
chown: Change file owner and group
chgrp: Change group ownership
chmod: Change access permissions
touch: Change file timestamps
df: Report file system disk space usage
du: Estimate file space usage
stat: Report file or file system status
sync: Synchronize cached writes to persistent storage
truncate: Shrink or extend the size of a file
echo: Print a line of text
printf: Format and print data
yes: Print a string until interrupted
false: Do nothing, unsuccessfully
true: Do nothing, successfully
test: Check file types and compare values
expr: Evaluate expressions
tee: Redirect output to multiple files or processes
basename: Strip directory and suffix from a file name
dirname: Strip last file name component
pathchk: Check file name validity and portability
mktemp: Create temporary file or directory
realpath: Print the resolved file name.
pwd: Print working directory
stty: Print or change terminal characteristics
printenv: Print all or some environment variables
tty: Print file name of terminal on standard input
id: Print user identity
logname: Print current login name
whoami: Print effective user ID
groups: Print group names a user is in
users: Print login names of users currently logged in
who: Print who is currently logged in
date: Print or set system date and time
arch: Print machine hardware name
nproc: Print the number of available processors
uname: Print system information
hostname: Print or set system name
hostid: Print numeric host identifier
uptime: Print system uptime and load
chcon: Change SELinux context of file
runcon: Run a command in specified SELinux context
chroot: Run a command with a different root directory
env: Run a command in a modified environment
nice: Run a command with modified niceness
nohup: Run a command immune to hangups
stdbuf: Run a command with modified I/O stream buffering
timeout: Run a command with a time limit
kill: Send a signal to processes
sleep: Delay for a specified time
factor: Print prime factors
numfmt: Reformat numbers
Possible units:
seq: Print numeric sequences
```

reload shell

    source source ~/.bash_profile #unix
    source source ~/.bash_profile #gitshell

add fabrigeas as sudoer

    sudo adduser fabrigeas sudo>
    getent group sudo

## ssh

```bash

sudo apt-get install openssh-server
sudo cp /etc/ssh/sshd_config /etc/ssh/sshd_config.original
sudo chmod a-w /etc/ssh/sshd_config.original

# check that ssh server is running on this computer
sudo netstat -anp | grep sshd

# connect
# fab is the name of a user registered on the host
ssh fab@ip.of.host.ie.remote.pc
ssh fab@dell # if the remote(server hostname -f =>dell)

# username not required because username is same in both client and server
ssh ip.of.remote.pc
ssh dell # (hostname -f on the remote returned dell)

# disconnect
exit

# login without password => using rsa-key
# on client
# copies public key client -> server.known hosts
ssh-copy-id username@ip.of.server.pc
scp copy files between computers

# without ssh login to the remte host
#  cd to your dest
scp fabrigeas@remote:/path/to/file/file.ext  /path/in/local/computer
scp jenny@192.168.2.113: C:/Users/feugang/Desktop/install-ssh-win8.txt .

# already ssh logged in to the remote
scp /path/to/file/file.ext  username@theOtherComputer:/path/to/dest

# examples
ssh fabrigeas@10.0.0.1 pwd
ssh fabrigeas@10.0.0.1 (any command)

```

### install ssh on windows

```bash
# https:# github.com/PowerShell/Win32-OpenSSH/wiki/Install-Win32-OpenSSH
# download https:# github.com/PowerShell/Win32-OpenSSH/releases/tag/v0.0.4.0
# Extract contents to C:\Program Files\OpenSSH
# Start Powershell as Administrator, then run the ff commands from power shell
cd 'C:\Program Files\OpenSSH'
# Install sshd and ssh-agent services.
powershell -executionpolicy bypass -file install-sshd.ps1
# Setup SSH host keys (this will generate all the 'host' keys that sshd expects when its starts)
.\ssh-keygen.exe -A
# Secure SSH host keys (optional)
Start-Service ssh-agent
# download psexec from: https:# technet.microsoft.com/en-us/sysinternals/pxexec.aspx
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
```

## AWK

[awk printf](http://www.freeos.com/guides/lsst/ch07sec05.html)

```bash
awk '/pattern/ {action} ' file1List
awk -F ":" '/pattern/ {action} ' file1List #set separator
awk -f fileContainingActions file1List
awk ' ! /pattern/ {action} ' fileList # inverse
awk ' $1 == "text" {action} ' fileList # match only the $1
awk ' $1 > 10  &&  $2 < 7 {action} ' fileList
awk ' ($1+$2 > 10) && ($3 < $4) {action} '

awk ' BEGIN {action_B}
/pattern1/ {action1}
/pattern2/ {action2}
END {action_E} fileList'

awk '/good/ { print $3 }' file  # foreach line containing /good/, print the 3$ column
awk '{ print $1 $2 "--> Rs." $3 * $4 }' file # $2:name, $3: quantity $4: price thus, compute each price

echo /bla/ { print $3 } > pattern.txt && awk -f pattern.txt file  # print the $3 colum of each line containing 'bla'

cat > comp_inv
3 > 5 { print $0 }
&& awk -f comp_inv file

# variables

# math
{
  total = $3 * $4
  recno = $1
    item = $2
  gtotal = gtotal + total
  print recno item " Rs." total " [Total Rs." gtotal "] "
}

# bill
{
  total = $3 * $4
  recno = $1
    item = $2
  print recno item " Rs." total
}

# file
1. Pen 5 20.00
2. Pencil 10 2.00
3. Rubber 3 3.50
4. Cock 2 45.50

# $ awk -f bill file

1.Pen Rs.100
2.Pencil Rs.20
3.Rubber Rs.10.5
4.Cock Rs.91

# bill2
BEGIN {
  print "---------------------------"
  print "Bill for the 4-March-2001. "
  print "By Vivek G Gite. "
  print "---------------------------"
}

{
  total = $3 * $4
  recno = $1
    item = $2
  gtotal += total
  print recno item " Rs." total
}

END {
  print "---------------------------"
  print "Total Rs." gtotal
  print "==========================="
}

```

## cut

```bash

cut -f1 file.txt # => 1st field of each line, tab separator.
echo a:b | cut -d: -f2 => 2nd field of each line, separator :.
echo a b c | cut -d" " -f1,3
echo a b c d e | cut -d" " -f1-3,5
echo a b c | cut -d" " -f3,2,1
echo "123456789" | cut  -c-3 => 123
echo "123456789" | cut -c2,-5 => 12345
echo "123456789" | cut -c2 => 2-9
echo "123" | cut  --complement -c2 13

echo a b c d | cut -d" " -f2- => "b c d".
echo abcd | cut -c3,4 #parse char rather than fields => "cd".
echo abcdefgh | cut -c1-3,6-8 => abcfgh

```
