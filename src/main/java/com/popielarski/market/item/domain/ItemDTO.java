package com.popielarski.market.item.domain;

import com.popielarski.market.common.Calculator;
import com.popielarski.market.common.domain.Value;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private String name;
    private Value price;
    private Integer quantity;
    private String barCode;

    public Value getTotalPrice(){
        return Calculator.multiple(quantity, price.getValue());
    }

    public ItemDTO increaseQuantity(Integer quantity){
        this.quantity += quantity;
        return this;
    }

}

