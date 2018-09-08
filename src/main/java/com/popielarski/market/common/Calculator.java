package com.popielarski.market.common;


import com.popielarski.market.common.domain.Value;
import com.popielarski.market.product.Price;
import lombok.AccessLevel;
import lombok.Setter;

import java.math.BigDecimal;

import static com.popielarski.market.common.MathUtils.mathContext;

@Setter(value = AccessLevel.PRIVATE)
public class Calculator {

    public static Value multiple(Integer quantity, BigDecimal value) {
        return Value.of(new BigDecimal(quantity).multiply(value).round(mathContext));
    }

    public static Value multiple(Integer quantity, Value value) {
        return Value.of(new BigDecimal(quantity).multiply(value.getValue()).round(mathContext));
    }

    public static Price multiple(Integer quantity, Price price) {
        return Price.of(new BigDecimal(quantity).multiply(price.getValue()));
    }

    public static Value subtract(Integer amount, Value price) {
        return Value.of(new BigDecimal(amount).subtract(price.getValue()));
    }

    public static Value add(BigDecimal firstValue, BigDecimal secondValue) {
        return Value.of(firstValue.add(secondValue).round(mathContext));
    }

    public static Value add(Value firstValue, Value secondValue) {
        return Value.of(firstValue.getValue().add(secondValue.getValue()).round(mathContext));
    }

    public static Price add(Price firstTotalPrice, Price secondTotalPrice) {
        return Price.of(firstTotalPrice.getValue().add(secondTotalPrice.getValue()));
    }

    public static Price multiple(Price price, String value) {
        return Price.of(price.getValue().multiply(new BigDecimal(value)).round(mathContext));
    }

    public static Price subtract(Price cartTotalPrice, Price valueToDecrease) {
        return Price.of(cartTotalPrice.getValue().subtract(valueToDecrease.getValue()).round(mathContext));
    }
}
