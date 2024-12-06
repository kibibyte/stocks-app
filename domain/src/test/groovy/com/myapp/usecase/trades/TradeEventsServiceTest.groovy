package com.myapp.usecase.trades

import com.myapp.usecase.trades.events.TradeEvent
import com.myapp.usecase.trades.events.TradeEventsRepository
import com.myapp.usecase.trades.events.TradeEventsService
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import spock.lang.Specification

class TradeEventsServiceTest extends Specification {

  private final TradeEventsRepository tradeEventsRepository = Mock()
  private final TradeEventsService tradeEventsService = new TradeEventsService(tradeEventsRepository)

  def 'should findAll single Trade Event by stockTicker'() {
    when:

    tradeEventsRepository.findAll() >> Flux.just(
        new TradeEvent(StockTicker.TSLA, 200),
        new TradeEvent(StockTicker.AGFY, 50)
    )
    Flux<TradeEvent> tradeEvents = tradeEventsService.findByTicker(stockTicker)

    then:
    StepVerifier.create(tradeEvents)
        .expectNext(expectedResult)
        .verifyComplete()

    where:
    stockTicker      || expectedResult
    StockTicker.TSLA || new TradeEvent(StockTicker.TSLA, 200)
  }

  def 'should findAll All Trade Events'() {
    when:
    tradeEventsRepository.findAll() >> Flux.just(
        new TradeEvent(StockTicker.TSLA, 200),
        new TradeEvent(StockTicker.AGFY, 50)
    )
    Flux<TradeEvent> tradeEvents = tradeEventsService.findAll()

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