package com.popielarski.market.cart;

import com.popielarski.market.item.ItemMapper;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CartMapper {

    private final ItemMapper itemMapper;

    public CartDTO toDTO(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .items(itemMapper.asDTO(cart.getItems()))
                .discount(cart.getDiscount())
                .build();
    }

}
