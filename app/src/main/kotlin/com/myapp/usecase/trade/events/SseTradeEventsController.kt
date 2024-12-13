package com.myapp.usecase.trade.events;

import com.myapp.usecase.trade.StockTicker
import io.micronaut.http.MediaType.TEXT_EVENT_STREAM
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.sse.Event
import reactor.core.publisher.Flux

@Controller
class SseTradeEventsController(private var tradeEventsService: TradeEventsService) {

  @Get(value = "/sse/trades", produces = [TEXT_EVENT_STREAM])
  fun streamAll(): Flux<Event<TradeEventData>> {
    return tradeEventsService
      .streamAll()
      .map { toSseEvent(it) }
  }

  @Get(value = "/sse/trades/{stockTicker}", produces = [TEXT_EVENT_STREAM])
  fun streamByTicker(@PathVariable stockTicker: StockTicker): Flux<Event<TradeEventData>> {
    return tradeEventsService
      .streamByTicker(stockTicker)
      .map { toSseEvent(it) }
  }

  private fun toSseEvent(event: TradeEvent): Event<TradeEventData> {
    return Event.of(TradeEventData(
      event.stockTicker,
      event.price
    ))
  }
}