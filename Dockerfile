# start with base image
FROM openjdk:11

LABEL maintainer="com.learn.scarycoders"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]