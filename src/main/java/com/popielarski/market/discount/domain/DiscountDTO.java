package com.popielarski.market.discount.domain;

import com.popielarski.market.common.domain.PriceDTO;
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
    private PriceDTO priceBeforeDiscount;
    private PriceDTO priceAfterDiscount;
}
