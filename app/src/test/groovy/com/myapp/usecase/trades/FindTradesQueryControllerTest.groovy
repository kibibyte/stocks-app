package com.myapp.usecase.trades

import com.myapp.usecase.trades.events.TradeEventData
import com.myapp.usecase.trades.events.TradeEventEntity
import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.sse.SseClient
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.Test
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import reactor.core.publisher.Flux

import static com.myapp.usecase.trades.StockTape.AMEX
import static com.myapp.usecase.trades.StockTape.NASDAQ
import static com.myapp.usecase.trades.StockTicker.*

@Testcontainers(disabledWithoutDocker = true)
class FindTradesQueryControllerTest {

  @Container
  static final KafkaContainer kafka = new KafkaContainer(
      DockerImageName.parse("confluentinc/cp-kafka:7.3.3")
  )

  def server = ApplicationContext.run(EmbeddedServer.class, getProperties())

  Map<String, String> getProperties() {
    if (!kafka.isRunning()) {
      kafka.start();
    }
    ["kafka.bootstrap.servers": kafka.getBootstrapServers()]
  }

  @Test
  void shouldGetAllSseEvents() {
    given:
    def sseClient = server.applicationContext.createBean(SseClient.class, server.URL)
    def kafkaEvents = createEvents()
    def expectedSseEvents = [
        new TradeEventData(AGFY, 222),
        new TradeEventData(SQSP, 456),
        new TradeEventData(TSLA, 53)
    ]

    when:
    def actualSseEvents = Flux.from(sseClient
        .eventStream("/sse/trades", TradeEventData.class))
        .doOnSubscribe {
          sendEvents(kafkaEvents)
        }
        .take(3)
        .map { it -> it.getData() }
        .collectList()
        .block()

    then:
    assert actualSseEvents.containsAll(expectedSseEvents)
  }

  @Test
  void shouldGetFilteredSseEventsByStockTicker() {
    given:
    def sseClient = server.applicationContext.createBean(SseClient.class, server.URL)
    def kafkaEvents = createEvents()
    def expectedSseEvents = [new TradeEventData(TSLA, 53)]

    when:
    def actualSseEvents = Flux.from(sseClient
        .eventStream("/sse/trades/${TSLA}", TradeEventData.class))
        .doOnSubscribe {
          sendEvents(kafkaEvents)
        }
        .take(1)
        .map { it -> it.getData() }
        .collectList()
        .block()

    then:
    assert actualSseEvents == expectedSseEvents
  }

  private static createEvents() {
    [
        new TradeEventEntity(AMEX, AGFY, 222, 12345),
        new TradeEventEntity(NASDAQ, SQSP, 456, 534345),
        new TradeEventEntity(NASDAQ, TSLA, 53, 345345)
    ]
  }

  private sendEvents(kafkaEvents) {
    def kafkaProducer = server.applicationContext.createBean(TradeEventsProducer.class, getProperties())
    new Thread({ _ ->
      try {
        for (event in kafkaEvents) {
          Thread.sleep(100)
          kafkaProducer.send(event)
        }
      } catch (Exception ignore) {
      }
    }).start()
  }
}
