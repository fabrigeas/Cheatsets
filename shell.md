# Shell programming

[http://www.freeos.com/guides/lsst/](http://www.freeos.com/guides/lsst/)\
[Advanced scripting guide](http://www.tldp.org/LDP/abs/html/)

```bash
# Execute a batch file

sh file.sh
bash file.sh
./file.sh
```

## Parameters

```bash
# file.sh a b 1 z

echo $0 # file.sh
echo $2 # b
echo $# # 4
```

## Misc

```bash
# append the scripts pwd to path
export PATH=$PATH:/path/to/script/

# Display Information
echo "user: ";whoami
echo "date ";date
exit 0

# System variables
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

# defined variables (no space between variable=value)

set user
name=feugang
surname=fabrice

echo $name
```

## Read users input

```bash

echo "Enter your first name please:"
read firstname
echo Your firstname is: \$firstname

read -p 'Username: ' username
read -sp 'Password: ' password
echo Username: $username Password: $password
```

## Iterations

```bash
# for
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


# while
COUNTER=0
while [  $COUNTER -lt 10 ]; do
  echo The counter is $COUNTER
  let COUNTER=COUNTER+1
done

# Until
COUNTER=20
until [  $COUNTER -lt 10 ]; do
  echo COUNTER $COUNTER
let COUNTER-=1
done

# break
a=0
while [ $a -lt 10 ] do
  echo $a
  if [ $a -eq 5 ]
    then
  break
  fi
  a= `expr $a + 1`
done

# continue
NUMS="1 2 3 4 5 6 7"
for NUM in $NUMS do
  Q='expr $NUM % 2'
  if [ $Q -eq 0 ] then
    echo "Number is an even number!!"
    continue
  fi
  echo "Found odd number"
done
```

## Conditions

```bash
# case
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


# if
a=1
b=2

if [ $a -ge $b ]; then
  echo a is greater than b
else
  echo b is greater than a
fi

a=10
b=20

if [ $a == $b ] then
    echo "a is equal to b"

elif [ $a -gt $b ] then
    echo "a is greater than b"

elif [ $a -lt $b ] then
    echo "a is less than b"
else
    echo "None of the condition met"
fi


# Interface1
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

# interface 2

if [ -z "$1" ]; then
  echo usage: $0 directory
  exit
fi

SRCD=$1
TGTD="/var/backups/"
OF=home-$(date +%Y%m%d).tgz
tar -cZf $TGTD$OF $SRCD
```

## Arithmetic evaluation

```bash
echo "1 +1 $((1+1))"
echo "1 + 1 =$[1+1]"

# Operators(comparators)
if [ 5 -eq 6 ] # ==
if [ 5 -ne 6 ] # !=
if [ 5 -lt 6 ] # <
if [ 5 -le 6 ] # <=
if [ 5 -gt 6 ] # >
if [ 5 -ge 6 ] # >=
```

## Functions

```bash
square() {
  return $(expr $1 + $2)
}

square 7 2
echo "returned: $?"

```

## Redirection

```bash
# redirect stdout to a file
ls -l > ls-l.txt

# redirect stderr to a file
grep da * 2> grep-errors.txt

# redirect stdout to a stderr
grep da * 1>&2

# redirect stderr to a stdout
grep * 2>&1

# redirect stderr and stdout to a file
rm -f $(find / -name core) &> /dev/null
```

## Substitutions

```bash

echo ${var:-"Variable is not set"} # Variable is not set
echo "1 - Value of var is ${var}" # 1 - Value of var is

echo ${var:="Variable is not set"} # Variable is not set
echo "2 - Value of var is ${var}" # 2 - Value of var is Variable is not set

unset var
echo ${var:+"This is default value"} # This is default value
echo "3 - Value of var is $var" # 3 - Value of var is

var="Prefix"
echo ${var:+"This is default value"}
echo "4 - Value of var is $var" # 4 - Value of var is Prefix

echo ${var:?"Print this message"} # Prefix
echo "5 - Value of var is ${var}" # 5 - Value of var is Prefix

```

## Arrays

```bash

Arrays{

NAME[0]="Zara"
NAME[1]="Qadir"
NAME[2]="Mahnaz"
NAME[3]="Ayan"
NAME[4]="Daisy"

echo "First Index: ${NAME[0]}" # Zara
echo "Second Index: ${NAME[1]}" # Quadir

# access all elements of the array
${array_name[*]}
${array_name[@]}
```

## [string formatting](https://www.tutorialspoint.com/unix/unix-shell-substitutions.htm)

```bash
# Special Characters
(Comma , )
let "t2 = ((a = 9, a / 3))" # executes comma separated expression and return only the last comma expression
```
