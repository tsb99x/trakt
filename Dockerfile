FROM adoptopenjdk:13-jdk-hotspot AS builder

WORKDIR /usr/src/trakt
COPY . .
RUN ./gradlew --info shadowJar

FROM adoptopenjdk:13-jre-hotspot

WORKDIR /usr/bin/trakt
COPY --from=builder /usr/src/trakt/build/libs/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
