#!/bin/bash
NEXT_WAIT_TIME=0
until [ $NEXT_WAIT_TIME -eq 10 ] || wget localhost:8080; do
    sleep $(( NEXT_WAIT_TIME++ ))
done
[ $NEXT_WAIT_TIME -lt 10 ]
