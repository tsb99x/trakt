# Trakt

This is an experiment on building a minimalistic app in Kotlin.

## Run in Docker

Use `docker-compose up` to build and start a fully functional instance of API server, DB included.

Default port mappings are:
- API Server: `49080`
- PostgreSQL: `49432`

## Development Tips

To start development, you will need to run `docker-compose up -d db`, to launch only a PostgreSQL instance in daemon mode.
Database schema will be populated and validated at application or tests launch, courtesy of FlyWay.

After that, use one of following commands:
- To just run application: `./gradlew run`
- To run test suite, use `./gradlew clean test integrationTest`.
