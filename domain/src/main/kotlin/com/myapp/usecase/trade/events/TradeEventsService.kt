package com.myapp.usecase.trade.events;

import com.myapp.usecase.trade.StockTicker
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

@Singleton
class TradeEventsService(private val tradeEventsRepository: TradeEventsRepository) {
  companion object {
    private val log = LoggerFactory.getLogger(TradeEventsService::class.java)
  }

  fun streamAll(): Flux<TradeEvent> {
    return tradeEventsRepository.streamAll()
  }

  fun streamByTicker(stockTicker: StockTicker): Flux<TradeEvent> {
    return tradeEventsRepository.streamAll()
      .filter { event -> event.stockTicker==stockTicker }
  }
}
