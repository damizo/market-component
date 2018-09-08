package com.popielarski.market.checkout.domain;

import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.item.domain.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CheckoutReceiptDTO {

    private Long finalPrice;
    private DiscountType appliedDiscount;
    private CheckoutProcessStatus status;
    private Set<ItemDTO> items;
    private Long change;

}
