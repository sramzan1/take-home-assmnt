FROM amazoncorretto:8

WORKDIR /app

COPY target/service1-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]