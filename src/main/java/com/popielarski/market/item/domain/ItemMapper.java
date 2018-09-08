package com.popielarski.market.item.domain;


import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


public class ItemMapper {

    public ItemDTO asDTO(Item item, Integer quantity) {
        return ItemDTO.builder()
                .quantity(quantity)
                .price(item.getProduct().getPrice().toValue())
                .name(item.getProduct().getName())
                .barCode(item.getProduct().getBarCode())
                .build();
    }

    public Item asDomain(ItemDTO item) {
        return Item.builder()
                .quantity(item.getQuantity())
                .build();
    }

    public Set<ItemDTO> asDTO(Set<Item> items) {
        return items.stream()
                .map(item -> asDTO(item, item.getQuantity()))
                .collect(Collectors.toSet());
    }
}
