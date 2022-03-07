FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY target/. .
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]