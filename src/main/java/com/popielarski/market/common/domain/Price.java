package com.popielarski.market.common.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Getter
public class Price {

    private static final MathContext mathContext = new MathContext(1, RoundingMode.UP);
    private BigDecimal value;

    private Price(BigDecimal value) {
        this.value = value.round(mathContext);
    }

    public static Price of(Integer value) {
        return new Price(fromInteger(value));
    }

    public static Price of(Long value) {
        return new Price(fromLong(value));
    }

    public static Price of(String value) {
        return new Price(fromString(value));
    }

    private static BigDecimal fromInteger(Integer value) {
        return new BigDecimal(value, mathContext);
    }

    private static BigDecimal fromString(String value) {
        return new BigDecimal(value, mathContext);
    }

    private static BigDecimal fromLong(Long value) {
        return new BigDecimal(value, mathContext);
    }
}
