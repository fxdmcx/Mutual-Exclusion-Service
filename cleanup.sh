#!/bin/bash

# Change this to your netid
netid=xxh163430

cat conf.txt | sed -e "s/#.*//" | sed -e "/^\s*$/d" |
(
    read i
    echo $i
    while read line
    do
        host=$( echo $line | awk '{ print $2 }' )

        echo $host
        ssh $netid@$host.utdallas.edu killall -u $netid &
        sleep 1
    done
   
)


echo "Cleanup complete"
