FROM openjdk:17
COPY ./build/libs/miml-backend-0.0.1-SNAPSHOT.jar miml-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","miml-backend-0.0.1-SNAPSHOT.jar"]
