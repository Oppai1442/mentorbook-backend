# Dockerfile cho Java Spring
FROM openjdk:21
VOLUME /tmp
COPY target/mentorbook-backend-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java", "-jar", "/myapp.jar"]