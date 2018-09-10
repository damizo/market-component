package com.popielarski.market.item;


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

    public Set<ItemDTO> asDTO(Set<Item> items) {
        return items.stream()
                .map(item -> asDTO(item, item.getQuantity()))
                .collect(Collectors.toSet());
    }
}
