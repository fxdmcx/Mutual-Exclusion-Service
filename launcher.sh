cat conf.txt | sed -e "s/#.*//" | sed -e "/^\s*$/d" |
(
    read i
    echo $i
    while read -r line || [[ -n $line ]];
    do
        host=$( echo $line | awk '{ print $2 }' )
        ssh xxh163430@$host.utdallas.edu "cd $HOME/AOS_Project2; java MutualServ" &
    done
   
)

