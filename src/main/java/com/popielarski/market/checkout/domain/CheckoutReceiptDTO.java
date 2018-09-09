package com.popielarski.market.checkout.domain;

import com.popielarski.market.common.domain.PriceDTO;
import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.item.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CheckoutReceiptDTO {

    private PriceDTO finalPrice;
    private DiscountType appliedDiscount;
    private CheckoutProcessStatus status;
    private Set<ItemDTO> items;
    private PriceDTO change;

}
