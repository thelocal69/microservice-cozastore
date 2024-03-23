#!/bin/bash

for PORT in 8761 8085 8080 8084 8082 8086 8089 8081 8083; do
  PID=$(lsof -t -i:$PORT)
  if [ -n "$PID" ]; then
    echo "Terminating process $PID running on port $PORT..."
    kill $PID
  fi
done


