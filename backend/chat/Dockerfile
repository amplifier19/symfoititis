FROM openjdk:17-jdk-alpine AS base 
WORKDIR /workspace/app
ARG DEPENDENCY=/workspace/app/target/dependency

FROM base AS builder
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM base 
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

ENV SPRING_PROFILES_ACTIVE=dev

EXPOSE 8081

ENTRYPOINT ["java","-cp","/app:/app/lib/*","gr.symfoititis.chat.ChatApplication"]

