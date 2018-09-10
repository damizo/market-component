package com.popielarski.market.discount.domain.boughttogether;

import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.product.domain.Product;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BoughtTogetherDiscountPair extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "FIRST_PRODUCT_ID")
    private Product firstProduct;

    @ManyToOne
    @JoinColumn(name = "SECOND_PRODUCT_ID")
    private Product secondProduct;

    @ManyToOne
    @JoinColumn(name="BOUGHT_TOGETHER_DISCOUNT_ID")
    private BoughtTogetherDiscount boughtTogetherDiscount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BoughtTogetherDiscountPair)) return false;

        BoughtTogetherDiscountPair that = (BoughtTogetherDiscountPair) o;

        return new EqualsBuilder()
                .append(firstProduct.getBarCode(), that.firstProduct.getBarCode())
                .append(secondProduct.getBarCode(), that.secondProduct.getBarCode())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstProduct.getBarCode())
                .append(secondProduct.getBarCode())
                .toHashCode();
    }
}
