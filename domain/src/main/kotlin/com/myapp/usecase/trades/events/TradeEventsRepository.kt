package com.myapp.usecase.trades.events;

import reactor.core.publisher.Flux

interface TradeEventsRepository {
  fun findAll(): Flux<TradeEvent>
}
