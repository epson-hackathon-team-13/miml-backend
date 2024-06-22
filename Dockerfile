FROM openjdk:17
ARG build/libs/*.jar
COPY ./build/libs/miml-backend.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]