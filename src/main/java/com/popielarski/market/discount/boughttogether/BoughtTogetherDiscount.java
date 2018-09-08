package com.popielarski.market.discount.boughttogether;

import com.google.common.collect.Sets;
import com.popielarski.market.discount.Discount;
import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.discount.DiscountUnit;
import lombok.Builder;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "BOUGH_TOGETHER_DISCOUNTS")
@Data
public class BoughtTogetherDiscount extends Discount {

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProductDiscountPair> productPairs = Sets.newHashSet();

    @Builder
    public BoughtTogetherDiscount(String description, Set<ProductDiscountPair> productPairs) {
        super(description, DiscountType.BOUGHT_TOGETHER, DiscountUnit.PERCENT);
        this.productPairs = productPairs;
    }

    public BoughtTogetherDiscount() {
        setUnit(DiscountUnit.PERCENT);
    }
}
