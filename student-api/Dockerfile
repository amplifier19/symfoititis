FROM openjdk:17-jdk-alpine as base 
WORKDIR /workspace/app
ARG DEPENDENCY=/workspace/app/target/dependency

FROM base as source
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

FROM source as stage-build
RUN mv -f /workspace/app/src/main/env/stage/* /workspace/app/src/main/resources
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM source as prod-build
RUN mv -f /workspace/app/src/main/env/prod/* /workspace/app/src/main/resources
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM base as stage-image
COPY --from=stage-build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=stage-build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=stage-build ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8081
ENTRYPOINT ["java","-cp","/app:/app/lib/*","gr.symfoititis.rest.student.StudentApplication"]

FROM base as prod-image      
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=prod-build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prod-build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prod-build ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8081
ENTRYPOINT ["java","-cp","/app:/app/lib/*","gr.symfoititis.rest.student.StudentApplication"]