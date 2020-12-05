# Running application by Dockerfile

## Pre-Build

Before build, execute:

**Atention**: make sure that the commands will be executed in the project's root folder


```
$ ./mvnw clean package -DskipTests
```

```
$ mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
```

- Reference: https://spring.io/guides/gs/spring-boot-docker/

## Building Image

```
$ docker build -t proposal-app .
```

## Running Image

```
$ docker run -e SPRING_PROFILES_ACTIVE='dev' -e JDBC_LOCATION='jdbc:postgresql://host.docker.internal:5433/proposal' -p 8080:8080  proposal-app
```

# Running application by Docker Compose

You must create and set up a `.env` file based on `.example-env`

Reference: https://docs.docker.com/compose/environment-variables/

# Tips

Inside a container, you can connect to localhost with: `host.docker.internal:PORT`