package com.myapp.usecase.trade.events;

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.messaging.annotation.MessageBody
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@KafkaListener(uniqueGroupId = true, offsetReset = OffsetReset.LATEST)
class KafkaTradeEventsRepository : TradeEventsRepository {
  private val sink: Sinks.Many<TradeEvent> = Sinks.many().multicast().directBestEffort()

  companion object {
    private val log = LoggerFactory.getLogger(KafkaTradeEventsRepository::class.java)
  }

  @Topic("trades")
  fun onEvent(@MessageBody tradeEventEntity: TradeEventEntity) {
    sink.tryEmitNext(TradeEvent(
      tradeEventEntity.stockTicker,
      tradeEventEntity.price
    ))
  }

  override fun streamAll(): Flux<TradeEvent> {
    return sink.asFlux()
  }
}