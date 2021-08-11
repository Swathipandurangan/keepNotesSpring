FROM java:8-jre
WORKDIR usr/src
EXPOSE 8765
ADD ./target/netflix-zuul-api-gateway-server-0.0.1-SNAPSHOT.jar /usr/src/netflix-zuul-api-gateway-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "netflix-zuul-api-gateway-server-0.0.1-SNAPSHOT.jar"]