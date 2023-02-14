ARG MICRO_NAME=users-microservice
#CONTRUIMOS LA IMAGEN de tipo alpine(OPTIMIZADO) CON alias builder o puede ser otra cosa {
FROM eclipse-temurin:17-alpine as builder

LABEL mentainer="erickhc.dev@gmail.com"

ARG MICRO_NAME

WORKDIR /app/$MICRO_NAME

#COPIAMOS EL POM PAREN A users-microservice
COPY ./pom.xml /app

#COPIAMOS EL mvn  A users-microservice
COPY ./$MICRO_NAME/.mvn ./.mvn

#COPIAMOS EL mvnw A users-microservice
COPY ./$MICRO_NAME/mvnw .

#COPIAMOS EL PON A users-microservice
COPY ./$MICRO_NAME/pom.xml .

# HACER QUE NO COPILE TEST, NI EJECUTE EL MAIN(codigo) , EMPAQUETA EL PROYECTO SIN EL CODIGO FUENTE ,Y  ELIMINAR EL TARGET
RUN ./mvnw  clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

# RUN ./mvnw dependency:go-offline -> OTRA MANERA DE HACER RUN LO DE ARRIBA

#COPIAMOS TODO EL SRC EN LA IMAGEN A users-microservice
COPY ./$MICRO_NAME/src ./src

RUN ./mvnw  clean package -DskipTests

#HASTA AQUI CONSTRUIMOS LA IMAGE }

#COPIAMOS EL BUILDER Y QUEDA MUCHO MAS OPTIMIZADO
FROM eclipse-temurin:17-alpine

ARG MICRO_NAME

WORKDIR /app

ARG TARGET_FOLDER=/app/$MICRO_NAME/target

COPY --from=builder $TARGET_FOLDER/users-microservice-0.0.1-SNAPSHOT.jar .

ENV PORT 8001

EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "users-microservice-0.0.1-SNAPSHOT.jar"]
