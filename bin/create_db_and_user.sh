#!/bin/sh

mysql_execute() {
	eval 'mysql -u root --password="" -e "$1";'
}

USER=wholesaler
HOST=localhost
FULL_USER="'${USER}'@'${HOST}'"
DB_NAME=wholesaler_development

mysql_execute "DROP USER IF EXISTS ${FULL_USER};"
mysql_execute "CREATE USER ${FULL_USER} IDENTIFIED by '${USER}';"

mysql_execute "DROP DATABASE IF EXISTS ${DB_NAME};"
mysql_execute "CREATE DATABASE ${DB_NAME};"

mysql_execute "GRANT ALL on ${DB_NAME}.* TO ${FULL_USER};"
