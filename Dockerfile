FROM openjdk:17
COPY ./build/libs/miml-backend-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
