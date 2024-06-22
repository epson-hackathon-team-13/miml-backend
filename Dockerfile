FROM openjdk:17
COPY ./build/libs/miml_backend.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
