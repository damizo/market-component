package com.popielarski.market.checkout.domain;

import com.popielarski.market.discount.Discount;
import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.item.domain.Item;
import com.popielarski.market.item.domain.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class CheckoutScanDTO {

    private Integer checkoutNumber;
    private Set<ItemDTO> items;
    private Long totalPrice;
    private Set<DiscountType> availableDiscounts;

}
