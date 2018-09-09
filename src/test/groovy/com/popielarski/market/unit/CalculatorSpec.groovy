package com.popielarski.market.unit

import com.popielarski.market.common.domain.Calculator
import com.popielarski.market.common.domain.PriceDTO
import com.popielarski.market.product.domain.Price
import spock.lang.Specification

class CalculatorSpec extends Specification {

    def "should multiple"() {
        expect:
        Calculator.multiple(firstValue, secondValue) == result

        where:
        firstValue       | secondValue         | result
        5                | new BigDecimal(100) | PriceDTO.of(500)
        10               | PriceDTO.of(40)     | PriceDTO.of(400)
        12               | Price.of("10")      | Price.of(120)
    }

    def "should add"() {
        expect:
        Calculator.add(firstValue, secondValue) == result

        where:
        firstValue           | secondValue            | result
        new BigDecimal("10") | new BigDecimal("45.5") | PriceDTO.of(55.5)
        PriceDTO.of("10.5")  | PriceDTO.of(10)        | PriceDTO.of(20.5)
        Price.of(3.75)       | Price.of(3)            | Price.of(6.75)
    }

    def "should subtract"() {
        expect:
        Calculator.subtract(firstValue, secondValue) == result

        where:
        firstValue                    | secondValue      | result
        Price.of(10.75)         | Price.of(3)            | Price.of(7.75)
        1000                          | PriceDTO.of(430) | PriceDTO.of(570)
    }


}
