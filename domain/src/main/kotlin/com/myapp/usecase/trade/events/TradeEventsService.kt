package com.myapp.usecase.trade.events;

import com.myapp.usecase.trade.StockTicker
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

@Singleton
class TradeEventsService(private val tradeEventsListener: TradeEventsListener) {
  companion object {
    private val log = LoggerFactory.getLogger(TradeEventsService::class.java)
  }

  fun streamAll(): Flux<TradeEvent> {
    return tradeEventsListener.streamAll()
  }

  fun streamByTicker(stockTicker: StockTicker): Flux<TradeEvent> {
    return tradeEventsListener.streamAll()
      .filter { event -> event.stockTicker==stockTicker }
  }
}
