#FROM openjdk:21
FROM eclipse-temurin:21-jdk-jammy AS builder

ARG MS_NAME=ms-usuarios-server

WORKDIR /app/$MS_NAME

COPY ./pom.xml /app
COPY ./$MS_NAME/.mvn ./.mvn
COPY ./$MS_NAME/mvnw .
COPY ./$MS_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MS_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

RUN mkdir ./logs

ARG MS_NAME=ms-usuarios-server

COPY --from=builder /app/$MS_NAME/target/ms-usuarios-server-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8001
ENV PORT=$PORT_APP
EXPOSE $PORT

#ENTRYPOINT ["java","-jar","ms-usuarios-server-0.0.1-SNAPSHOT.jar"]
CMD ["java","-jar","ms-usuarios-server-0.0.1-SNAPSHOT.jar"]