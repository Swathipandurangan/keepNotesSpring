FROM java:8-jre
WORKDIR usr/src
EXPOSE 8761
ADD ./target/netflix-eureka-naming-server-0.0.1-SNAPSHOT.jar /usr/src/netflix-eureka-naming-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "netflix-eureka-naming-server-0.0.1-SNAPSHOT.jar"]