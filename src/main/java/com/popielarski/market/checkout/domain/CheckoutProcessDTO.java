package com.popielarski.market.checkout.domain;

import com.popielarski.market.item.ItemDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class CheckoutProcessDTO extends CheckoutStatusDTO {

    private Integer actualPrice;
    private CheckoutProcessStatus processStatus;
    private Set<ItemDTO> paidItems;
}
