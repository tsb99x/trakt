FROM gradle:6.0 AS builder
WORKDIR /usr/src/trakt
COPY . .
RUN gradle test bootJar

FROM openjdk:11-jre
WORKDIR /usr/bin/trakt
COPY --from=builder /usr/src/trakt/api/build/libs/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]
