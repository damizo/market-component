package com.popielarski.market.checkout.domain;

import com.popielarski.market.item.domain.ItemDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class CheckoutProcessDTO extends CheckoutStatusDTO {

    private Integer actualPrice;
    private CheckoutProcessStatus processStatus;
    private Set<ItemDTO> paidItems;

  /*  @lombok.Builder
    public CheckoutProcessDTO(Integer checkoutNumber, CheckoutStatus status,
                              Integer actualPrice, CheckoutProcessStatus processStatus,
                              Set<ItemDTO> paidItems) {
        super(checkoutNumber, status);
        this.actualPrice = actualPrice;
        this.processStatus = processStatus;
        this.paidItems = paidItems;
    }*/
}
