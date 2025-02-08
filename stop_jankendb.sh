#!/bin/bash

HSQLDB_HOME="/opt/hsqldb_274"
# データベース識別名称
DB_NAME="jankendb"

# PID ファイル
PID_FILE="$HSQLDB_HOME/$DB_NAME/${DB_NAME}_server.pid"

# PID を確認して停止
if [ -f "$PID_FILE" ]; then
  PID=$(cat "$PID_FILE")
  echo "Stopping HSQLDB server with PID: $PID"
  kill "$PID" && rm -f "$PID_FILE"
  echo "HSQLDB server stopped."
else
  echo "No PID file found. Is the server running?"
fi
