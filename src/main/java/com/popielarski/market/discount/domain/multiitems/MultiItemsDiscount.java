package com.popielarski.market.discount.domain.multiitems;

import com.google.common.collect.Sets;
import com.popielarski.market.discount.domain.Discount;
import com.popielarski.market.discount.domain.DiscountType;
import com.popielarski.market.discount.domain.DiscountUnit;
import com.popielarski.market.discount.domain.boughttogether.BoughtTogetherDiscount;
import com.popielarski.market.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MULTI_ITEMS_DISCOUNTS")
@Getter
@Setter
public class MultiItemsDiscount extends Discount {

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Product> products = Sets.newHashSet();

    @Builder
    public MultiItemsDiscount(String description, Set<Product> products) {
        super(description, DiscountType.MULTI_ITEMS, DiscountUnit.VALUE);
        this.products = products;
    }

    public MultiItemsDiscount() {
        setUnit(DiscountUnit.VALUE);
    }

    public void addProduct(Product product) {
        this.getProducts().add(product);
        product.setMultiItemsDiscount(this);
    }

}
