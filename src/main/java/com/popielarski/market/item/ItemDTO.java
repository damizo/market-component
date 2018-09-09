package com.popielarski.market.item;

import com.popielarski.market.common.domain.Calculator;
import com.popielarski.market.common.domain.PriceDTO;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private String name;
    private PriceDTO price;
    private Integer quantity;
    private String barCode;

    public PriceDTO getTotalPrice(){
        return Calculator.multiple(quantity, price.getValue());
    }

    public ItemDTO increaseQuantity(Integer quantity){
        this.quantity += quantity;
        return this;
    }

}

