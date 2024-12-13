package com.myapp.usecase.trade.events

import com.myapp.usecase.trade.StockTicker

data class TradeEvent(
  val stockTicker: StockTicker,
  val price: Number
)