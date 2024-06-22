FROM openjdk:17
COPY ./build/libs/miml-backend-0.0.1-SNAPSHOT.jar /home/ec2-user/miml-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","app.jar"]
