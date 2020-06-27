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

./file.sh a b c d e f

    echo $0 //myprogram
    echo $1 //a
    echo $5 //f
    

## Misc

append the scripts pwd to path

    export PATH=$PATH:/path/to/script/
      

Display Information

    echo "user: ";whoami
    echo "date ";date
    exit 0
    

System variables

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
    

Set user defined variables (no space between variable=value)

    name=feugang
    surname=fabrice
    

Read users input

    echo "Your first name please:"
    read fname
    
    read -p "-p 'prompt message:' "

Processes

    ps #list all
    ps -ag
    kill  1012
    killall httpd
    kill 0 #killall except shell
    ps ax | grep httpd # is apache running??

# Loops

for

      for i in $( ls ); do
            echo item: $i
        done
        
        for var1 in 1 2 3
        do
           for var2 in 0 5
           do
              if [ $var1 -eq 2 -a $var2 -eq 0 ]
              then
                 break 2
              else
                 echo "$var1 $var2"
              fi
           done
        done  

while        

    COUNTER=0
    while [  $COUNTER -lt 10 ]; do
	         echo The counter is $COUNTER
	      let COUNTER=COUNTER+1 
      done      

Untill

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

      -eq	is equal to	5 == 6	if test 5 -eq 6	if [ 5 -eq 6 ]
      -ne	is not equal to	5 != 6	if test 5 -ne 6	if [ 5 -ne 6 ]
      -lt	is less than	5 < 6	if test 5 -lt 6	if [ 5 -lt 6 ]
      -le	is less than or equal to	5 <= 6	if test 5 -le 6	if [ 5 -le 6 ]
      -gt	is greater than	5 > 6	if test 5 -gt 6	if [ 5 -gt 6 ]
      -ge	is greater than or equal to	5 >= 6	if test 5 -ge 6	if [ 5 -ge 6 ]
      

Functions

      Hello () {
         echo "Hello World"
      }
      
      #Invoke your function
      Hello
      
      
      Hello () {
         echo "Hello World $1 $2"
         return 10
      }
      Hello Zara Ali
      retun_from_last_functio_ie_100=$?
      echo "Return value is :$retun_from_last_functio_ie_100"

Special Variables

	test.sh
	echo "File Name: $0"
	echo "First Parameter : $1"
	echo "Second Parameter : $2"
	echo "Quoted Values: $@"
	echo "Quoted Values: $*"
	echo "Total Number of Parameters : $#"

	$./test.sh Zara Ali
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
    

Binary operators

      -eq (arg1 is equal to arg2)
      -eq (arg1 is equal to arg2)
      -ne (arg1 is not equal to arg2)
      -lt (arg1 is less than arg2)
      -le (arg1 is less than or equal to arg2)
      -gt (arg1 is greater than arg2)
      -ge (arg1 is greater than or equal to arg2)

[string formating](https://www.tutorialspoint.com/unix/unix-shell-substitutions.htm)

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
