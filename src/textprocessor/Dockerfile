FROM adoptopenjdk/openjdk11:latest

ENV        APP_PATH=~/textprocessor
WORKDIR    $APP_PATH
COPY       . .

RUN        ./gradlew clean build -x test
RUN        cp build/libs/*.jar app.jar
EXPOSE     8080
ENTRYPOINT ["java", "-jar", "app.jar"]
