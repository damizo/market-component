package com.popielarski.market.common.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.popielarski.market.common.MoneySerializer;
import com.popielarski.market.product.domain.Price;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class PriceDTO implements Serializable {

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal value;

    private Currency currency;

    private PriceDTO(BigDecimal value, Currency currency) {
        this.value = value.setScale(2, RoundingMode.HALF_EVEN);
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
        return new BigDecimal(value);
    }

    private static BigDecimal fromString(String value) {
        return new BigDecimal(value).setScale(2, RoundingMode.UNNECESSARY);
    }

    private static BigDecimal fromLong(Long value) {
        return new BigDecimal(value).setScale(2, RoundingMode.UNNECESSARY);
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public Price toPrice(Currency currency) {
        return new Price(value, currency);
    }

}
