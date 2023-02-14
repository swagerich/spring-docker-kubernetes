#CONTRUIMOS LA IMAGEN de tipo alpine(OPTIMIZADO) CON alias builder o puede ser otra cosa {
FROM eclipse-temurin:17-alpine as builder

LABEL mentainer="erickhc.dev@gmail.com"

WORKDIR /app/course-microservice

#COPIAMOS EL POM PAREN A users-microservice
COPY ./pom.xml /app

#COPIAMOS EL mvn  A users-microservice
COPY ./course-microservice/.mvn ./.mvn

#COPIAMOS EL mvnw A users-microservice
COPY ./course-microservice/mvnw .

#COPIAMOS EL PON A users-microservice
COPY ./course-microservice/pom.xml .

# HACER QUE NO COPILE TEST, NI EJECUTE EL MAIN(codigo) , EMPAQUETA EL PROYECTO SIN EL CODIGO FUENTE ,Y  ELIMINAR EL TARGET
#RUN ./mvnw  clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

#-> OTRA MANERA DE HACER RUN LO DE ARRIBA
RUN ./mvnw dependency:go-offline

#COPIAMOS TODO EL SRC EN LA IMAGEN A users-microservice
COPY ./course-microservice/src ./src

RUN ./mvnw  clean package -DskipTests

#HASTA AQUI CONSTRUIMOS LA IMAGE }

#COPIAMOS EL BUILDER Y QUEDA MUCHO MAS OPTIMIZADO
FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY --from=builder /app/course-microservice/target/course-microservice-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "course-microservice-0.0.1-SNAPSHOT.jar"]
