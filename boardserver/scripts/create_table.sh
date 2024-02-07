#!/bin/bash
PATH=$(pwd)/ddl/create_table.sql
MYSQL_PATH=/opt/homebrew/bin/mysql
USERNAME=board
DATABASE=board
$MYSQL_PATH -u $USERNAME -p $DATABASE < $PATH
