# Start with a base image containing Java runtime
FROM amazoncorretto:17.0.10-alpine3.19 AS build

LABEL version="1.0"

# Set the current working directory inside the image
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

# Package the application
RUN ./mvnw package -DskipTests
RUN apk add --no-cache postgresql-client
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Start with a base image containing Java runtime
FROM amazoncorretto:17.0.10-alpine3.19

VOLUME /tmp

# Copy the dependency application file from build stage
ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

COPY src/main/resources/application-prod.properties /app/application-prod.properties

HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java","-cp","app:app/lib/*","com.millio.task.TaskApplication"]