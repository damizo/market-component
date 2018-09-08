package com.popielarski.market.item;

import com.popielarski.market.item.domain.Item;
import com.popielarski.market.product.Product;

public class ItemFactory {

    public Item create(Product product){
        return Item.builder()
                .product(product)
                .quantity(Item.DEFAULT_QUANTITY)
                .build();
    }
}
