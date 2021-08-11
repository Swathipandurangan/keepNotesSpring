FROM openjdk:8-alpine
EXPOSE 9300
ADD /target/NoteService-0.0.1-SNAPSHOT.jar NoteService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "NoteService-0.0.1-SNAPSHOT.jar"]