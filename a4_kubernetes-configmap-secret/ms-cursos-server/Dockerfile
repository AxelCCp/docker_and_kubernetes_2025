#FROM openjdk:21
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app/ms-cursos-server

COPY ./pom.xml /app
COPY ./ms-cursos-server/.mvn ./.mvn
COPY ./ms-cursos-server/mvnw .
COPY ./ms-cursos-server/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./ms-cursos-server/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/ms-cursos-server/target/ms-cursos-server-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

#ENTRYPOINT ["java","-jar","ms-usuarios-server-0.0.1-SNAPSHOT.jar"]

CMD ["java","-jar","ms-cursos-server-0.0.1-SNAPSHOT.jar"]