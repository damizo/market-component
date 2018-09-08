package com.popielarski.market.product;

import com.popielarski.market.common.Currency;
import com.popielarski.market.common.MathUtils;
import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.common.domain.Value;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "PRICES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price extends BaseEntity implements Serializable {

    @Column(name = "VALUE")
    private BigDecimal value;

    @Column(name = "CURRENCY")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public BigDecimal getValue() {
        return this.value.round(MathUtils.mathContext);
    }

    public static Price of(BigDecimal value) {
        return new Price(value, Currency.PLN);
    }

    public static Price of(Integer value) {
        return new Price(new BigDecimal(value), Currency.PLN);
    }

    public static Price of(String value) {
        return new Price(new BigDecimal(value), Currency.PLN);
    }

    public static Price of(BigDecimal value, Currency currency) {
        return new Price(value, currency);
    }

    public static Price of(String value, Currency currency) {
        return Price.of(new BigDecimal(value), currency);
    }

    public static Price zero() {
        return Price.of(BigDecimal.ZERO);
    }

    public Value toValue() {
        return Value.of(this);
    }

    public static BigDecimal bigDecimalOf(String value) {
        return new BigDecimal(value, MathUtils.mathContext);
    }
}
