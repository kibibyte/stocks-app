# Stocks app (Kotlin, Reactor, Kafka, Server side events, Micronaut)

Trade events (SSE) Hot Publisher POC

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

Example request (Test trade events kafka producer is enabled):

```shell script
curl --request GET \
  --url http://localhost:8080/sse/trades \
  --header 'Content-Type: text/event-stream'
```

```shell script
curl --request GET \
  --url http://localhost:8080/sse/trades/TSLA \
  --header 'Content-Type: text/event-stream'
```

![trade_events](https://github.com/user-attachments/assets/8a9e316a-4726-441f-a6e0-05f9710c4dc4)