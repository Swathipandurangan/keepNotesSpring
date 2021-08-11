FROM openjdk:8-alpine
EXPOSE 9200
ADD /target/UserService-0.0.1-SNAPSHOT.jar UserService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "UserService-0.0.1-SNAPSHOT.jar"]