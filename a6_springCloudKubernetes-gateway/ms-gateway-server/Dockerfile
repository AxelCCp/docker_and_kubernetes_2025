#FROM openjdk:21
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app/ms-gateway-server

COPY ./pom.xml /app
COPY ./ms-gateway-server/.mvn ./.mvn
COPY ./ms-gateway-server/mvnw .
COPY ./ms-gateway-server/pom.xml .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline

COPY ./ms-gateway-server/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/ms-gateway-server/target/ms-gateway-server-0.0.1-SNAPSHOT.jar .

EXPOSE 8090

#ENTRYPOINT ["java","-jar","ms-gateway-server-0.0.1-SNAPSHOT.jar"]

CMD ["java","-jar","ms-gateway-server-0.0.1-SNAPSHOT.jar"]