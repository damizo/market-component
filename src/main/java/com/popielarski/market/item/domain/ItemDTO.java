package com.popielarski.market.item.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private String name;
    private Long price;
    private Integer quantity;
    private String barCode;

    public Long getTotalPrice(){
        return quantity * price;
    }

    public ItemDTO increaseQuantity(Integer quantity){
        this.quantity += quantity;
        return this;
    }

}

