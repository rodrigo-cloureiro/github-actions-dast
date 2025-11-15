FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/dast-demo-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 7000

CMD ["java", "-jar", "app.jar"]
