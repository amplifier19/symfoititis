FROM openjdk:17-jdk-alpine AS base 
WORKDIR /workspace/app
ARG DEPENDENCY=/workspace/app/target/dependency

FROM base AS source
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

FROM source AS stage-build
RUN mv -f /workspace/app/src/main/env/stage/* /workspace/app/src/main/resources
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM source AS prod-build
RUN mv -f /workspace/app/src/main/env/prod/* /workspace/app/src/main/resources
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM base AS stage-image
COPY --from=stage-build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=stage-build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=stage-build ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8081
ENTRYPOINT ["java","-cp","/app:/app/lib/*","gr.symfoititis.rest.admin.AdminApplication"]

FROM base AS prod-image      
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=prod-build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prod-build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prod-build ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8081
ENTRYPOINT ["java","-cp","/app:/app/lib/*","gr.symfoititis.rest.admin.AdminApplication"]
