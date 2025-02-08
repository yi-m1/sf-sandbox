#!/bin/bash

HSQLDB_HOME="/opt/hsqldb_274"

# HSQLDB の lib
HSQLDB_LIB_PATH="$HSQLDB_HOME/hsqldb.jar"

# データベース識別名称
DB_NAME="jankendb"

# データベースファイル
DB_PATH="$HSQLDB_HOME/$DB_NAME/dbfile/$DB_NAME"

# ログファイル
LOG_FILE="$HSQLDB_HOME/$DB_NAME/${DB_NAME}_server.log"

# サーバーをバックグラウンドで起動
nohup java -cp "$HSQLDB_LIB_PATH" org.hsqldb.server.Server --database.0 file:"$DB_PATH" --dbname.0 "$DB_NAME" > "$LOG_FILE" 2>&1 &

# PID を保存
echo $! > "$HSQLDB_HOME/$DB_NAME/${DB_NAME}_server.pid"

# echo "HSQLDB server for 'janken' started. PID: $(cat janken_server.pid)"

