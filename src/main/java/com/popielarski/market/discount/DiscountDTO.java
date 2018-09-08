package com.popielarski.market.discount;

import com.popielarski.market.cart.CartDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO {
    private DiscountType type;
    private Long priceBeforeDiscount;
    private Long priceAfterDiscount;
}
