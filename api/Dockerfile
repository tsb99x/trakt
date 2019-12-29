FROM adoptopenjdk:13-jdk-hotspot AS builder

WORKDIR /usr/src/trakt
COPY . .
RUN ./gradlew --info api:shadowJar

FROM adoptopenjdk:13-jre-hotspot

WORKDIR /usr/bin/trakt
COPY --from=builder /usr/src/trakt/api/build/libs/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
