FROM openjdk:14-alpine
COPY build/libs/*.jar tourguide-user-service.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "tourguide-user-service.jar"]