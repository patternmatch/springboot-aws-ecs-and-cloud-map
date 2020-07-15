FROM adoptopenjdk/openjdk11:latest

ENV        APP_PATH=~/textprocessor
WORKDIR    $APP_PATH
COPY       . .

HEALTHCHECK --interval=2s --timeout=1s --retries=3 CMD curl --fail --silent http://localhost:8080/actuator/health || exit 1

RUN        ./gradlew clean build -x test
RUN        cp build/libs/*.jar app.jar
EXPOSE     8080
ENTRYPOINT ["java", "-jar", "app.jar"]
