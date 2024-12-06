package com.myapp.usecase.trades.events

import com.myapp.usecase.trades.StockTape
import com.myapp.usecase.trades.StockTicker
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class TradeEventEntity(
  val tape: StockTape,
  val stockTicker: StockTicker,
  val price: Number,
  val sip: Int
)