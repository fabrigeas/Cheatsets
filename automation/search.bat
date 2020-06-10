@echo off
set /p path=Enter file: 
echo %path%

echo "searching :"
echo %1 
echo %2 
echo %3

find path -type f \( -iname \%1 -o -iname \*%2 -o -iname \*%3 \)

find path -iname "*.sh"
