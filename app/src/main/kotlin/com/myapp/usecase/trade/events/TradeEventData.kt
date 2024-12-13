package com.myapp.usecase.trade.events;

import com.myapp.usecase.trade.StockTicker
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
data class TradeEventData(
  val stockTicker: StockTicker,
  val price: Number
)