FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ARG WAR_FILE=target/backend-cats-0.0.3-SNAPSHOT.war
ADD ${WAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]