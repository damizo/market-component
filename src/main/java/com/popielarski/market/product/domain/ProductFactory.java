package com.popielarski.market.product.domain;

import com.popielarski.market.common.domain.PriceDTO;

class ProductFactory {
    public Product create(String name, PriceDTO price, Integer quantity, String barCode) {
        return Product.builder()
                .barCode(barCode)
                .price(Price.of(price.getValue()))
                .quantity(quantity)
                .name(name)
                .build();
    }
}
