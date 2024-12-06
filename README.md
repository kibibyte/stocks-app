# Stock app application (Kotlin, Reactor, Kafka, Server side events, Micronaut)

## Modules

* app
* data
* domain

## Build

```shell script
$ ./gradlew build
```

## Test

```shell script
$ ./gradlew test
```

## Running locally

```shell script
$ docker compose up
```

Example request (Test trade events producer is enabled):

```shell script
curl --request GET \
  --url http://localhost:8080/sse/trades \
  --header 'Content-Type: text/event-stream'
```

![image](https://github.com/user-attachments/assets/48383037-78ae-483f-9047-f5f080743d8d)

