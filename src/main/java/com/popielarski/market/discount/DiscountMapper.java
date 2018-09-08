package com.popielarski.market.discount;

import com.popielarski.market.cart.CartDTO;

public class DiscountMapper {
    public DiscountDTO toDiscountDTO(Long totalPriceBeforeDiscount, Long totalPriceAfterDiscount, DiscountType type) {
        return DiscountDTO.builder()
                .priceAfterDiscount(totalPriceAfterDiscount)
                .priceBeforeDiscount(totalPriceBeforeDiscount)
                .type(type)
                .build();
    }
}
