package com.myapp.usecase.trades.tools

import com.myapp.usecase.trades.StockTape
import com.myapp.usecase.trades.StockTicker
import com.myapp.usecase.trades.events.TradeEventEntity
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Requires
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton

@Singleton
@Requires(property = "app.enableTestKafkaProducer", value = "true", defaultValue = "false")
class TestProducer(private val client: MyKafkaClient) {
  private val stockTickers = StockTicker.entries.toTypedArray()
  private val stockTapes = StockTape.entries.toTypedArray()

  @Scheduled(fixedRate = "1s")
  fun generateEvents() {
    client.newTrade(TradeEventEntity(
      stockTapes.random(),
      stockTickers.random(),
      (0..1000).random(),
      (0..100000).random(),
    ))
  }

  @KafkaClient
  interface MyKafkaClient {
    @Topic("trades")
    fun newTrade(tradeEventEntity: TradeEventEntity)
  }
}