package com.popielarski.market.cart;

import com.google.common.collect.Sets;
import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.item.domain.ItemDTO;
import com.popielarski.market.item.domain.ItemMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
public class CartMapper {

    private final ItemMapper itemMapper;

    public CartDTO toDTO(Set<ItemDTO> items, DiscountType discountType){
        return CartDTO.builder()
                .discount(discountType)
                .items(items)
                .build();
    }

    public CartDTO toDTO(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .items(itemMapper.asDTO(cart.getItems()))
                .discount(cart.getDiscount())
                .build();
    }

}
