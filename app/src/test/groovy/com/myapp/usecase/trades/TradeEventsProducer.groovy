package com.myapp.usecase.trades

import com.myapp.usecase.trade.events.TradeEventEntity
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient("Test trade events producer")
interface TradeEventsProducer {
  @Topic("trades")
  void send(TradeEventEntity tradeEvent)
}
