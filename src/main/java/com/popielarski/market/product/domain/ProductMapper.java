package com.popielarski.market.product.domain;

import com.popielarski.market.common.domain.PriceDTO;

public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .barCode(product.getBarCode())
                .price(PriceDTO.of(product.getPrice()))
                .build();
    }
}
