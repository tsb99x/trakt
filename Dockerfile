FROM gradle:5.6.0-jdk12 AS builder
WORKDIR /usr/src/trakt
COPY . .
RUN gradle bootJar

FROM openjdk:12
WORKDIR /usr/bin/trakt
COPY --from=builder /usr/src/trakt/build/libs/trakt-*.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]
