FROM openjdk:8
ADD target/appliancecontrol-0.0.1-SNAPSHOT.jar appliancecontrol-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "appliancecontrol-0.0.1-SNAPSHOT.jar"]