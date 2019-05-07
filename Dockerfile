FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM java:8-jdk-alpine
COPY --from=build /home/app/target/data-0.0.1-SNAPSHOT.jar /usr/local/lib/data.jar
COPY --from=build /home/app/target/dependency-jars /usr/local/lib/dependency-jars
WORKDIR /usr/app
RUN sh -c 'touch data-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","/usr/local/lib/data.jar"]