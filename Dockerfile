FROM gradle:5.6 AS builder
WORKDIR /usr/src/trakt
COPY . .
RUN gradle test bootJar

FROM openjdk:11-jre
WORKDIR /usr/bin/trakt
COPY --from=builder /usr/src/trakt/build/libs/trakt-*.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]
