package com.myapp.usecase.trades.events;

import com.myapp.usecase.trades.StockTicker
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
data class TradeEventData(
  val stockTicker: StockTicker,
  val price: Number
)