package com.popielarski.market.item.domain;

import com.google.common.collect.Sets;
import com.popielarski.market.discount.Discount;
import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.discount.DiscountUnit;
import com.popielarski.market.product.Product;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "MULTI_ITEMS_DISCOUNTS")
@Getter
@Setter
public class MultiItemsDiscount extends Discount {

    @OneToMany(mappedBy = "multiItemsDiscount")
    private Set<Product> products = Sets.newHashSet();

    @Builder
    public MultiItemsDiscount(String description, Set<Product> products) {
        super(description, DiscountType.MULTI_ITEMS, DiscountUnit.VALUE);
        this.products = products;
    }

    public MultiItemsDiscount() {
        setUnit(DiscountUnit.VALUE);
    }
}
