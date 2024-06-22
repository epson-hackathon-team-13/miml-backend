FROM openjdk:17
WORKDIR /
COPY ./build/libs/miml.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
