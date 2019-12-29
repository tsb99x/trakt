# Trakt

Just a simple activity tracker

## Run in Docker

Use `docker-compose up` to build and start fully functional instance of Trakt Web and API servers, DB included.
It is not recommended to run such setup in production and provided only as a reference for environment configuration.

Default port mappings are:
- Trakt Web Server: `49088`
- Trakt API Server: `49080`
- PostgreSQL: `49432`

## Development Tips

To start development, you will need to run `docker-compose up -d db`, to launch only PostgreSQL instance in daemon mode.
Database schema will be populated and validated at application or tests launch, courtesy of FlyWay.

After that, use one of following commands:
- To just run application: `./gradlew api:run` or `./gradlew web:run`
- To run only unit tests, use `./gradlew clean test`.
- To run full test suite, use `./gradlew clean test integrationTest`.

## Modules

Project split into several modules to separate different parts of Trakt:
- `core` is for main logic (e.g. services layer) and database access.
- `api-model` is for request/response models of `api` module.
- `api` is for RESTful API interface.
- `web` is for Web UI interface.
