package com.myapp.usecase.trade.events;

import reactor.core.publisher.Flux

interface TradeEventsListener {
  fun streamAll(): Flux<TradeEvent>
}
