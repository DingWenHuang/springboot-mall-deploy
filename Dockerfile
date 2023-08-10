FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar springboot-mall0726-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080