package com.popielarski.market.checkout.domain;

import com.popielarski.market.common.domain.Value;
import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.item.domain.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CheckoutScanDTO {

    private Integer checkoutNumber;
    private Set<ItemDTO> items;
    private Value totalPrice;
    private Set<DiscountType> availableDiscounts;

}
