#!/bin/bash

set -e  # stop on error

REPO_DIR="/home/akashsrvstv64/Spring-API-Service-Setup"
JAR_NAME="app.jar"
LOG_FILE="/home/akashsrvstv64/logs/deploy.log"

echo "=============================="
echo " Deployment Started: $(date)"
echo "==============================" | tee -a $LOG_FILE

cd $REPO_DIR || exit 1

echo "? Cleaning repo..." | tee -a $LOG_FILE
git reset --hard origin/main
git clean -fd

echo "? Pulling latest code..." | tee -a $LOG_FILE
git pull origin main

echo "? Locating pom.xml..." | tee -a $LOG_FILE
POM_PATH=$(find . -name "pom.xml" | head -n 1)

if [ -z "$POM_PATH" ]; then
  echo "? ERROR: pom.xml not found" | tee -a $LOG_FILE
  exit 1
fi

PROJECT_DIR=$(dirname "$POM_PATH")
cd $PROJECT_DIR

export GEMINI_API_KEY="GEMINI_API_KEY"

echo "? Building project..." | tee -a $LOG_FILE
mvn clean package -DskipTests

JAR_PATH=$(find target -name "*.jar" | head -n 1)

if [ -z "$JAR_PATH" ]; then
  echo "? ERROR: JAR not found after build" | tee -a $LOG_FILE
  exit 1
fi

echo "? Copying JAR..." | tee -a $LOG_FILE
cp $JAR_PATH /home/akashsrvstv64/$JAR_NAME

echo "🧹 Cleaning target folder after successful copy..." | tee -a $LOG_FILE
rm -rf target

echo "? Stopping old app..." | tee -a $LOG_FILE
pkill -f $JAR_NAME || true

PID=$(lsof -ti:8080 || true)

if [ ! -z "$PID" ]; then
  echo "⚠ Port 8080 busy, killing PID: $PID" | tee -a $LOG_FILE
  kill -9 $PID || true
fi

sleep 3

echo "? Starting new app..." | tee -a $LOG_FILE
nohup java -jar /home/akashsrvstv64/$JAR_NAME > /home/akashsrvstv64/logs/app.log 2>&1 &

sleep 10

RESPONSE=$(curl -s http://localhost:8080/actuator/health)

echo "$RESPONSE" | grep -q '"status"[[:space:]]*:[[:space:]]*"UP"'

if [ $? -eq 0 ]; then
  echo "Deployment Successful!" | tee -a $LOG_FILE
else
  echo "Health check failed!" | tee -a $LOG_FILE
  exit 1
fi
echo "=============================="
echo " Deployment Finished: $(date)"
echo "==============================" | tee -a $LOG_FILE