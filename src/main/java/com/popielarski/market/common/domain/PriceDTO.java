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

    private PriceDTO(BigDecimal value) {
        this.value = value.round(mathContext);
    }

    public static PriceDTO of(Integer value) {
        return new PriceDTO(fromInteger(value));
    }

    public static PriceDTO of(Long value) {
        return new PriceDTO(fromLong(value));
    }

    public static PriceDTO of(String value) {
        return new PriceDTO(fromString(value));
    }

    public static PriceDTO of(BigDecimal value) {
        return new PriceDTO(value);
    }

    public static PriceDTO of(Price price) {
        return new PriceDTO(price.getValue());
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
