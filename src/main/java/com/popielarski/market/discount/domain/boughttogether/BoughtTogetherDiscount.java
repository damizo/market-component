package com.popielarski.market.discount.domain.boughttogether;

import com.google.common.collect.Sets;
import com.popielarski.market.discount.domain.Discount;
import com.popielarski.market.discount.domain.DiscountType;
import com.popielarski.market.discount.domain.DiscountUnit;
import com.popielarski.market.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "BOUGH_TOGETHER_DISCOUNTS")
@Getter
@Setter
public class BoughtTogetherDiscount extends Discount {

    @OneToMany(cascade = CascadeType.ALL)
    private Set<BoughtTogetherDiscountPair> productPairs = Sets.newHashSet();

    @Builder
    public BoughtTogetherDiscount(String description, Set<BoughtTogetherDiscountPair> productPairs) {
        super(description, DiscountType.BOUGHT_TOGETHER, DiscountUnit.PERCENT);
        this.productPairs = productPairs;
    }

    public BoughtTogetherDiscount() {
        setUnit(DiscountUnit.PERCENT);
    }


    public void addPair(BoughtTogetherDiscountPair boughtTogetherDiscountPair) {
        this.productPairs.add(boughtTogetherDiscountPair);
        boughtTogetherDiscountPair.setBoughtTogetherDiscount(this);

        Product firstProduct = boughtTogetherDiscountPair.getFirstProduct();
        Product secondProduct = boughtTogetherDiscountPair.getSecondProduct();

        boughtTogetherDiscountPair.setFirstProduct(firstProduct);
        boughtTogetherDiscountPair.setSecondProduct(secondProduct);

        firstProduct.setBoughtTogetherDiscount(this);
        secondProduct.setBoughtTogetherDiscount(this);
    }
}
