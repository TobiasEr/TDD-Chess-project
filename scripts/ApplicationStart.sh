#!/bin/bash
cd /webapps/chess
echo "This version has an if!"
if [ ! -f "chess.pid" ]; 
then
  java -jar chess-0.0.1-SNAPSHOT.jar > chess.log 2> /dev/null < /dev/null &
  echo $! > chess.pid
fi
exit 0
