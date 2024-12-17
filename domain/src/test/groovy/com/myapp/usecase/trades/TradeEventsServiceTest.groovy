package com.myapp.usecase.trades

import com.myapp.usecase.trade.StockTicker
import com.myapp.usecase.trade.events.TradeEvent
import com.myapp.usecase.trade.events.TradeEventsListener
import com.myapp.usecase.trade.events.TradeEventsService
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import spock.lang.Specification

class TradeEventsServiceTest extends Specification {

  private final TradeEventsListener tradeEventsListener = Mock()
  private final TradeEventsService tradeEventsService = new TradeEventsService(tradeEventsListener)

  def 'should stream single Trade Event by stockTicker'() {
    when:

    tradeEventsListener.streamAll() >> Flux.just(
        new TradeEvent(StockTicker.TSLA, 200),
        new TradeEvent(StockTicker.AGFY, 50)
    )
    Flux<TradeEvent> tradeEvents = tradeEventsService.streamByTicker(stockTicker)

    then:
    StepVerifier.create(tradeEvents)
        .expectNext(expectedResult)
        .verifyComplete()

    where:
    stockTicker      || expectedResult
    StockTicker.TSLA || new TradeEvent(StockTicker.TSLA, 200)
  }

  def 'should stream All Trade Events'() {
    when:
    tradeEventsListener.streamAll() >> Flux.just(
        new TradeEvent(StockTicker.TSLA, 200),
        new TradeEvent(StockTicker.AGFY, 50)
    )
    Flux<TradeEvent> tradeEvents = tradeEventsService.streamAll()

    then:
    StepVerifier.create(tradeEvents)
        .expectNext(expectedResult1)
        .expectNext(expectedResult2)
        .verifyComplete()

    where:
    stockTicker      || expectedResult1                       | expectedResult2
    StockTicker.TSLA || new TradeEvent(StockTicker.TSLA, 200) | new TradeEvent(StockTicker.AGFY, 50)
  }
}