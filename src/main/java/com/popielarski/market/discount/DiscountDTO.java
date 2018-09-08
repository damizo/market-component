package com.popielarski.market.discount;

import com.popielarski.market.common.domain.Value;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO {
    private DiscountType type;
    private Value priceBeforeDiscount;
    private Value priceAfterDiscount;
}
