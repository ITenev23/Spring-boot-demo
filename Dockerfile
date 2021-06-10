FROM openjdk:12
VOLUME /tmp 
EXPOSE 8080 
ADD /target/apiskeleton-1.0-SNAPSHOT.jar apiskeleton-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","apiskeleton-1.0-SNAPSHOT.jar"]