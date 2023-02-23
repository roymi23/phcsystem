FROM eclipse-temurin:17-jre

ARG JAR_FILE=target/phc.jar
COPY ${JAR_FILE} identity-svc.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/identity-svc.jar"]