package com.popielarski.market.discount.domain;

import com.popielarski.market.common.domain.PriceDTO;

class DiscountMapper {
    public DiscountDTO toDiscountDTO(PriceDTO totalPriceBeforeDiscount, PriceDTO totalPriceAfterDiscount, DiscountType type) {
        return DiscountDTO.builder()
                .priceAfterDiscount(totalPriceAfterDiscount)
                .priceBeforeDiscount(totalPriceBeforeDiscount)
                .type(type)
                .build();
    }
}
