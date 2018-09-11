package com.popielarski.market.common.domain;


import com.popielarski.market.product.domain.Price;

import java.math.BigDecimal;


public class Calculator {

    public static PriceDTO multiple(Integer firstValue, BigDecimal secondValue) {
        return PriceDTO.of(new BigDecimal(firstValue).multiply(secondValue));
    }

    public static PriceDTO multiple(Integer firstValue, PriceDTO secondValue) {
        return PriceDTO.of(new BigDecimal(firstValue).multiply(secondValue.getValue()));
    }

    public static Price multiple(Integer firstValue, Price secondValue) {
        return Price.of(new BigDecimal(firstValue).multiply(secondValue.getValue()));
    }

    public static PriceDTO subtract(Integer firstValue, PriceDTO secondValue) {
        return PriceDTO.of(new BigDecimal(firstValue).subtract(secondValue.getValue()));
    }

    public static PriceDTO add(BigDecimal firstValue, BigDecimal secondValue) {
        return PriceDTO.of(firstValue.add(secondValue));
    }

    public static PriceDTO add(PriceDTO firstValue, PriceDTO secondValue) {
        return PriceDTO.of(firstValue.getValue().add(secondValue.getValue()));
    }

    public static Price add(Price firstPrice, Price secondPrice) {
        return Price.of(firstPrice.getValue().add(secondPrice.getValue()));
    }

    public static Price multiple(Price firstPrice, String secondValue) {
        return Price.of(firstPrice.getValue().multiply(new BigDecimal(secondValue)));
    }

    public static Price subtract(Price firstPrice, Price secondPrice) {
        return Price.of(firstPrice.getValue().subtract(secondPrice.getValue()));
    }
}
