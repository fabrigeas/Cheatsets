echo "enter next filename. Type '0' in an empty line to end:"
i=0 
path[0]="hello"
while [ $i -ne -1 ]; 
	do 
		read path[$i]
		if [ "${path[$i]}" = "0" ]
		then
			break
		fi
		let i=i+1 
	done;

# print the whola array 'path'
#~ echo ${path[*]}
#~ echo ${path[@]}

# build the search command
#~ filename="\( -iname *.$1 -o -iname *. \)"
filename="find . \\( -iname \"*."${path[0]}\"

if [ ${#path[@]} -gt 1 ];then
	i=1
	while [  $i -lt ${#path[@]} ]; do
		if [ "${path[$i]}" = "0" ];then
			filename+=" \\)"
			break
		fi
		filename+=" -o -iname \"*."${path[$i]}\"
		let i=i+1
	done
fi

echo $filename
$filename;

read -p "type any key to exit"


#~ echo "enter path:"
#~ read path
#~ echo "find $path $1 $2 $3"
#~ find $path -type f \( -name "*.$1" -o -name "*.$2" -o -name "*.$3" \)
#~ find $path -iname "*.sh"
