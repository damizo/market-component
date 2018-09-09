package com.popielarski.market.common.domain;


import com.popielarski.market.product.domain.Price;

import java.math.BigDecimal;

import static com.popielarski.market.common.utils.MathUtils.mathContext;

    public class Calculator {

    public static PriceDTO multiple(Integer firstValue, BigDecimal secondValue) {
        return PriceDTO.of(new BigDecimal(firstValue).multiply(secondValue).round(mathContext));
    }

    public static PriceDTO multiple(Integer firstValue, PriceDTO secondValue) {
        return PriceDTO.of(new BigDecimal(firstValue).multiply(secondValue.getValue()).round(mathContext));
    }

    public static Price multiple(Integer firstValue, Price secondValue) {
        return Price.of(new BigDecimal(firstValue).multiply(secondValue.getValue()));
    }

    public static PriceDTO subtract(Integer firstValue, PriceDTO secondValue) {
        return PriceDTO.of(new BigDecimal(firstValue).subtract(secondValue.getValue()));
    }

    public static PriceDTO add(BigDecimal firstValue, BigDecimal secondValue) {
        return PriceDTO.of(firstValue.add(secondValue).round(mathContext));
    }

    public static PriceDTO add(PriceDTO firstValue, PriceDTO secondValue) {
        return PriceDTO.of(firstValue.getValue().add(secondValue.getValue()).round(mathContext));
    }

    public static Price add(Price firstPrice, Price secondPrice) {
        return Price.of(firstPrice.getValue().add(secondPrice.getValue()));
    }

    public static Price multiple(Price firstPrice, String secondValue) {
        return Price.of(firstPrice.getValue().multiply(new BigDecimal(secondValue)).round(mathContext));
    }

    public static Price subtract(Price firstPrice, Price secondPrice) {
        return Price.of(firstPrice.getValue().subtract(secondPrice.getValue()).round(mathContext));
    }
}
