# Shell cheatset
[http://www.freeos.com/guides/lsst/](http://www.freeos.com/guides/lsst/)
[Advanced scripting guide](http://www.tldp.org/LDP/abs/html/)

Execute batch file: type the following in the shell

``` 
sh file.sh
bash file.sh
./file.sh
```

### Parameters

``` 
// file.sh
echo first param: $0
echo first param: $1
echo second param: $2 
echo $3 

sh file.sh

// output
first params: shell-tutorial.sh
first param: hello
second param: world
from
the
other
```

## Misc

append the scripts pwd to path

    export PATH=$PATH:/path/to/script/

Display Information

``` 
echo "user: ";whoami
echo "date ";date
exit 0
```

    

System variables

``` 
$BASH
$BASH_VERSION
$COLUMNS
$HOME
$LINES
$LOGNAME
$OSTYPE
$PATH
$PS1
$PWD
$SHELL
$USERNAME
```

Set user defined variables (no space between variable=value)

    name=feugang
    surname=fabrice

    echo $name

Read users input

    echo "Enter your first name please:"
    read firstname
    echo Your firstname is: $firstname

    read -p 'Username: ' username
    read -sp 'Password: ' password
    echo
    echo Username: $username Password: $password

Processes

    ps #list all
    ps -ag
    kill  1012
    killall httpd
    kill 0 #killall except shell
    ps ax | grep httpd # is apache running??

## Loops

for

    for i in $(ls); do
      echo item: $i
    done

    for even in 0 2 4 6 8; do

      for odd in 1 3 5 7; do
        if [ $even -eq 2 -a $odd -eq 0 ]; then
          break 2
        else
          echo "$even $odd"
        fi
      done
    done

while        

    COUNTER=0
    while [  $COUNTER -lt 10 ]; do
	         echo The counter is $COUNTER
	      let COUNTER=COUNTER+1 
      done      

Until

    COUNTER=20
    until [  $COUNTER -lt 10 ]; do
      echo COUNTER $COUNTER
    let COUNTER-=1
    done

break

    a=0
    while [ $a -lt 10 ]
    do
      echo $a
      if [ $a -eq 5 ]
        then
      break
      fi
      a= `expr $a + 1` 
    done

continue

    NUMS="1 2 3 4 5 6 7"
    for NUM in $NUMS
    do
      Q='expr $NUM % 2'
      if [ $Q -eq 0 ]
      then
          echo "Number is an even number!!"
          continue
      fi
      echo "Found odd number"
    done     

## switch

    while :
     do
        clear
        echo "-------------------------------------"
        echo " Main Menu "
        echo "-------------------------------------"
        echo "[1] Show Todays date/time"
        echo "[2] Show files in current directory"
        echo "[3] Show calendar"
        echo "[4] Start editor to write letters"
        echo "[5] Exit/Stop"
        echo "======================="
        echo -n "Enter your menu choice [1-5]: "
        read yourch
        case $yourch in
          1) echo "Today is `date` , press a key. . ." ; read ;;
          2) echo "Files in `pwd` " ; ls -l ; echo "Press a key. . ." ; read ;;
          3) cal ; echo "Press a key. . ." ; read ;;
          4) vi ;;
          5) exit 0 ;;
          *) echo "Opps!!! Please select choice 1,2,3,4, or 5";
             echo "Press a key. . ." ; read ;;
     esac
    done

## Conditions

if

    a=1
    b=2
    if [ $a -ge $b ]; then
      echo a is greater than b
    else
      echo b is greater than a
    fi

    a=10
    b=20
    if [ $a == $b ]
    then
        echo "a is equal to b"
    elif [ $a -gt $b ]
    then
        echo "a is greater than b"
    elif [ $a -lt $b ]
    then
        echo "a is less than b"
    else
        echo "None of the condition met"
    fi
           

   or

    a=10
    b=20
    if [ $a == $b ]; then
        echo "a is equal to b"
    elif [ $a -gt $b ]; then
        echo "a is greater than b"
    elif [ $a -lt $b ]; then
        echo "a is less than b"
    else
        echo "None of the condition met"
    fi    
      

Interface1

    OPTIONS="Hello Quit"
    select opt in $OPTIONS; do
    if [ "$opt" = "Quit" ]; then
      echo done
    exit
    elif [ "$opt" = "Hello" ]; then
      echo Hello World
    else
      clear
    echo bad option
    fi
    done

interface 2

    if [ -z "$1" ]; then 
      echo usage: $0 directory
      exit
    fi
    SRCD=$1
    TGTD="/var/backups/"
    OF=home-$(date +%Y%m%d).tgz
    tar -cZf $TGTD$OF $SRCD

Arithmetic evaluation

      echo "1 +1 $((1+1))"
      echo "1 + 1 =$[1+1]"
      

Operators(comparators)

    if [ 5 -eq 6 ] # ==
    if [ 5 -ne 6 ] # !=
    if [ 5 -lt 6 ] # <
    if [ 5 -le 6 ] # <=
    if [ 5 -gt 6 ] # >
    if [ 5 -ge 6 ] # >=
      

## Functions

    square() {
      return $(expr $1 + $2)
    }

    square 7 2
    echo "returned: $?"

Special Variables

	// test.sh
	echo "File Name: $0"
	echo "First Parameter : $1"
	echo "Second Parameter : $2"
	echo "Quoted Values: $@"
	echo "Quoted Values: $*"
	echo "Total Number of Parameters : $#"

	// sh test.sh Zara Ali

	File Name : ./test.sh
	First Parameter : Zara
	Second Parameter : Ali
	Quoted Values: Zara Ali
	Quoted Values: Zara Ali
	Total Number of Parameters : 2

Redirection

    redirect stdout to a file
    ls -l > ls-l.txt

    redirect stderr to a file
    grep da * 2> grep-errors.txt

    redirect stdout to a stderr
    grep da * 1>&2 

    redirect stderr to a stdout
    grep * 2>&1

    redirect stderr and stdout to a file
    rm -f $(find / -name core) &> /dev/null 

Substitutions

    echo ${var:-"Variable is not set"}
    echo "1 - Value of var is ${var}"

    echo ${var:="Variable is not set"}
    echo "2 - Value of var is ${var}"

    unset var
    echo ${var:+"This is default value"}
    echo "3 - Value of var is $var"

    var="Prefix"
    echo ${var:+"This is default value"}
    echo "4 - Value of var is $var"

    echo ${var:?"Print this message"}
    echo "5 - Value of var is ${var}"

    Upon execution, you will receive the following result −

    Variable is not set
    1 - Value of var is
    Variable is not set
    2 - Value of var is Variable is not set

    3 - Value of var is
    This is default value
    4 - Value of var is Prefix
    Prefix
    5 - Value of var is Prefix

    Arrays{

    NAME[0]="Zara"
    NAME[1]="Qadir"
    NAME[2]="Mahnaz"
    NAME[3]="Ayan"
    NAME[4]="Daisy"

    echo "First Index: ${NAME[0]}"
    echo "Second Index: ${NAME[1]}"

    //access all elements of the array
    ${array_name[*]}
    ${array_name[@]}

[string formatting](https://www.tutorialspoint.com/unix/unix-shell-substitutions.htm)

    echo ${var:-"Variable is not set"}
    echo "1 - Value of var is ${var}"

    echo ${var:="Variable is not set"}
    echo "2 - Value of var is ${var}"

    unset var
    echo ${var:+"This is default value"}
    echo "3 - Value of var is $var"

    var="Prefix"
    echo ${var:+"This is default value"}
    echo "4 - Value of var is $var"

    echo ${var:?"Print this message"}
    echo "5 - Value of var is ${var}"

Special Characters

## , (Comma) 

    let "t2 = ((a = 9, a / 3))" # executes comma separated expression and return only the last comma expression

## process

    ps # To see currently running process
    kill  1012 # To stop any process by PID i.e. to kill process
    killall httpd ##kill all apache processes
    ps -ag # get information about all running process
    kill 0 #To stop all process except your shell
    For background processing (With &, use to put particular command and program in background)	linux-command  &	$ ls / -R | wc -l &
    ps aux #display the owner of the processes along with the processes 
    ps ax | grep  process-U-want-to see #To see if a particular process is running or not
    ps ax | grep httpd #see whether Apache web server process is running or not then give command
    To see currently running processes and other information like memory and CPU usage with real time updates.	top
    See the output of top command.	
    $ top

    Note that to exit from top command press q.
    To display a tree of processes	pstree	$ pstree

## tools

    cut -f2 file.ext // extract row 2 of file
    paste file1 file //merge rows from different files
    tr "h2" "3x" < file // replace all h=>3, 2=>x
    tr "[a-z]" "[A-Z]" < file // toUppercase
    uniq file // remove duplicate lines

### awk

    awk '/good/ { print $3 }' file //foreach line containing /good/, print the 3$ column
    awk '{ print $1 $2 "--> Rs." $3 * $4 }' file # $2:name, $3: quantity $4: price thus, compute each price

    echo /bla/ { print $3 } > pattern.txt && awk -f pattern.txt file  //print the $3 colum of each line containing 'bla'

    cat > comp_inv
    3 > 5 { print $0 }
    && awk -f comp_inv file
    

variables

``` 
//math
{
  total = $3 * $4
  recno = $1
  item = $2
  gtotal = gtotal + total
  print recno item " Rs." total " [Total Rs." gtotal "] "
}
//bill
{
  total = $3 * $4
  recno = $1
  item = $2
  print recno item " Rs." total
}

//file

1. Pen     5  20.00
2. Pencil 10   2.00
3. Rubber  3   3.50
4. Cock    2  45.50

awk -f bill file

1.Pen Rs.100
2.Pencil Rs.20
3.Rubber Rs.10.5
4.Cock Rs.91

//bill2
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

[awk printf](http://www.freeos.com/guides/lsst/ch07sec05.html)

## [Practie](http://www.freeos.com/guides/lsst/ch08.html)
