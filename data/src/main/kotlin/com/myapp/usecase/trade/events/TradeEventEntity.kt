package com.myapp.usecase.trade.events

import com.myapp.usecase.trade.StockTape
import com.myapp.usecase.trade.StockTicker
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class TradeEventEntity(
  val tape: StockTape,
  val stockTicker: StockTicker,
  val price: Number,
  val sip: Int
)