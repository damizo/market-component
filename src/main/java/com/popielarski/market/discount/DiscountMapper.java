package com.popielarski.market.discount;

import com.popielarski.market.cart.CartDTO;
import com.popielarski.market.common.domain.Value;

public class DiscountMapper {
    public DiscountDTO toDiscountDTO(Value totalPriceBeforeDiscount, Value totalPriceAfterDiscount, DiscountType type) {
        return DiscountDTO.builder()
                .priceAfterDiscount(totalPriceAfterDiscount)
                .priceBeforeDiscount(totalPriceBeforeDiscount)
                .type(type)
                .build();
    }
}
