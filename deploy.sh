#!/usr/bin/sh

set -e

mvn clean
mvn install


mv ./target/JsfLogin.war /mnt/c/program\ files/apache\ software\ foundation/tomcat\ 9.0/webapps

echo "Successfully moved war to Tomcat"

cd /mnt/c/Program\ Files/Apache\ Software\ Foundation/Tomcat\ 9.0/bin

echo "Starting Tomcat"
pwd

sleep 1

cmd.exe /c startup.bat
