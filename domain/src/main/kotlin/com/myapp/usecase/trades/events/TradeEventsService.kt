package com.myapp.usecase.trades.events;

import com.myapp.usecase.trades.StockTicker
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

@Singleton
class TradeEventsService(private val tradeEventsRepository: TradeEventsRepository) {
  companion object {
    private val log = LoggerFactory.getLogger(TradeEventsService::class.java)
  }

  fun findAll(): Flux<TradeEvent> {
    return tradeEventsRepository.findAll()
  }

  fun findByTicker(stockTicker: StockTicker): Flux<TradeEvent> {
    return tradeEventsRepository.findAll()
      .filter { event -> event.stockTicker==stockTicker }
  }
}
