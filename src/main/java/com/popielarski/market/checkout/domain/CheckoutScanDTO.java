package com.popielarski.market.checkout.domain;

import com.popielarski.market.common.domain.PriceDTO;
import com.popielarski.market.discount.domain.DiscountType;
import com.popielarski.market.item.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CheckoutScanDTO {

    private Integer checkoutNumber;
    private Set<ItemDTO> items;
    private PriceDTO totalPrice;

}
