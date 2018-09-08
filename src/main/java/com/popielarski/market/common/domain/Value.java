package com.popielarski.market.common.domain;

import com.popielarski.market.common.Currency;
import com.popielarski.market.product.Price;

import java.io.Serializable;
import java.math.BigDecimal;

import static com.popielarski.market.common.MathUtils.mathContext;

public class Value implements Serializable {

    private BigDecimal value;

    private Value(BigDecimal value) {
        this.value = value.round(mathContext);
    }

    public static Value of(Integer value) {
        return new Value(fromInteger(value));
    }

    public static Value of(Long value) {
        return new Value(fromLong(value));
    }

    public static Value of(String value) {
        return new Value(fromString(value));
    }

    public static Value of(BigDecimal value) {
        return new Value(value);
    }

    public static Value of(Price price) {
        return new Value(price.getValue());
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

    public BigDecimal getValue() {
        return this.value;
    }

    public Price toPrice(Currency currency){
        return new Price(value, currency);
    }

}
