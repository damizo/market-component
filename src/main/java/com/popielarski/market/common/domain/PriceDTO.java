package com.popielarski.market.common.domain;

import com.popielarski.market.product.domain.Price;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

import static com.popielarski.market.common.utils.MathUtils.mathContext;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class PriceDTO implements Serializable {

    private BigDecimal value;

    private Currency currency;

    private PriceDTO(BigDecimal value, Currency currency) {
        this.value = value.round(mathContext);
        this.currency = currency;
    }

    public static PriceDTO of(Integer value) {
        return new PriceDTO(fromInteger(value), Currency.PLN);
    }

    public static PriceDTO of(Long value) {
        return new PriceDTO(fromLong(value), Currency.PLN);
    }

    public static PriceDTO of(String value) {
        return new PriceDTO(fromString(value), Currency.PLN);
    }

    public static PriceDTO of(BigDecimal value) {
        return new PriceDTO(value, Currency.PLN);
    }

    public static PriceDTO of(Price price) {
        return new PriceDTO(price.getValue(), Currency.PLN);
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

    public Price toPrice(Currency currency) {
        return new Price(value, currency);
    }

}
