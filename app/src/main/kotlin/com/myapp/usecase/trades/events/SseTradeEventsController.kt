package com.myapp.usecase.trades.events;

import com.myapp.usecase.trades.StockTicker
import io.micronaut.http.MediaType.TEXT_EVENT_STREAM
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.sse.Event
import reactor.core.publisher.Flux

@Controller
class SseTradeEventsController(private var tradeEventsService: TradeEventsService) {

  @Get(value = "/sse/trades", produces = [TEXT_EVENT_STREAM])
  fun findAll(): Flux<Event<TradeEventData>> {
    return tradeEventsService
      .findAll()
      .map { toSseEvent(it) }
  }

  @Get(value = "/sse/trades/{stockTicker}", produces = [TEXT_EVENT_STREAM])
  fun findByTicker(@PathVariable stockTicker: StockTicker): Flux<Event<TradeEventData>> {
    return tradeEventsService
      .findByTicker(stockTicker)
      .map { toSseEvent(it) }
  }

  private fun toSseEvent(event: TradeEvent): Event<TradeEventData> {
    return Event.of(TradeEventData(
      event.stockTicker,
      event.price
    ))
  }
}