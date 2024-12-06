package com.myapp.usecase.trades.events

import com.myapp.usecase.trades.StockTicker

data class TradeEvent(
  val stockTicker: StockTicker,
  val price: Number
)