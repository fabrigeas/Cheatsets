echo "enter path:"
read path
echo "find $path $1 $2 $3"
find $path -type f \( -iname $1 -o -iname $2 -o -iname $3 \)
#~ find $path -iname "*.sh"
