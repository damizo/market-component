package com.popielarski.market.cart;

import com.popielarski.market.discount.domain.DiscountType;
import com.popielarski.market.item.ItemDTO;
import com.popielarski.market.item.ItemMapper;
import lombok.RequiredArgsConstructor;

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
