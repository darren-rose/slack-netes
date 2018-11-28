FROM openjdk:8-jdk-alpine
ADD ./build/libs/slack-netes-*.jar /deployments/app.jar
ENTRYPOINT exec /usr/bin/java $JAVA_OPTS -jar /deployments/app.jar
