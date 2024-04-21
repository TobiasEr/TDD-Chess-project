#!/bin/bash
cd /webapps/chess
echo "This version has an if!"
if [ -f "chess.pid" ]; then
  kill -15 $(cat chess.pid)
  rm chess.pid
fi
