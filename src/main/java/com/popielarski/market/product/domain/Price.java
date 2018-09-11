package com.popielarski.market.product.domain;

import com.popielarski.market.common.domain.Currency;
import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.common.domain.PriceDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "PRICES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price extends BaseEntity implements Serializable {

    @Column(name = "VALUE")
    private BigDecimal value;

    @Column(name = "CURRENCY")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public BigDecimal getValue() {
        return this.value.setScale(2, RoundingMode.HALF_EVEN);
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

    public PriceDTO toValue() {
        return PriceDTO.of(this);
    }

}
