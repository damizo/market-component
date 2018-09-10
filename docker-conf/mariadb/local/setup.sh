#!/bin/bash
chmod +xr /mysql/init-script.sql
chmod +xr /mysql/setup.sh
set -e
service mysql start
mysql < /mysql/init-script.sql
service mysql stop