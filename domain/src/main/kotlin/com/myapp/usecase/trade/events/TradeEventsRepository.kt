package com.myapp.usecase.trade.events;

import reactor.core.publisher.Flux

interface TradeEventsRepository {
  fun streamAll(): Flux<TradeEvent>
}
