#!/bin/bash

JAVA=/usr/bin/java
APPLICATION_NAME=app
APPLICATION_DIR=/opt/bp-prod
APPLICATION_JAR="$APPLICATION_DIR/$APPLICATION_NAME.jar"
APPLICATION_LOG_DIR="$APPLICATION_DIR/logs"
APPLICATION_PID_FILE="$APPLICATION_DIR/run/application.pid"


export JAVA_OPTS="${JAVA_OPTS} $SW_AGENT_JAVA_PARAM \
-server -Xmx5g -Xms5g -Xmn2g -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m -Xss256k -XX:SurvivorRatio=8 \
-XX:+UseParNewGC  -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 \
-XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly \
-Djava.security.egd=file:/dev/./urandom \
-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${APPLICATION_LOG_DIR} \
"

SPRING_PROFILES="--spring.config.location=classpath:/ --spring.profiles.active=prod  --spring.pid.file=$APPLICATION_PID_FILE"

echo "starting application $APPLICATION_NAME"
nohup ${JAVA} ${JAVA_OPTS} -jar ${APPLICATION_JAR} $SPRING_PROFILES 1>/opt/bp-prod/logs/start.log 2>&1 &

WAIT_TIME=20

echo "wait for $WAIT_TIME seconds to check wether the process is running"


echo "${JAVA} ${JAVA_OPTS} -jar ${APPLICATION_JAR} $SPRING_PROFILES"



sleep $WAIT_TIME

if [ ! -f $APPLICATION_PID_FILE ]; then
    echo "no pid file for this application found, expect $APPLICATION_PID_FILE"
    exit 1
fi

PID=`cat $APPLICATION_PID_FILE`
if [ ! -n "$PID" ]; then
    echo "no pid for this application found"
    exit 1
fi
echo "get pid $PID"

if test $( ps ax | awk '{ print $1 }' | grep -e "^$PID$" |wc -l ) -eq 0
then
    echo "the process $PID is not running, please check log"
    exit 1
fi

echo "application started, pid:$PID"
