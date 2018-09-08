package com.popielarski.market.repository;

import com.popielarski.market.common.domain.InMemoryRepository;
import com.popielarski.market.item.domain.Item;
import com.popielarski.market.item.domain.ItemRepository;

public class ItemInMemoryRepository extends InMemoryRepository<Item> implements ItemRepository {

    /*@Override
    public Optional<Item> findByBarCode(String barCode) {
        return map.entrySet()
                .stream()
                .filter((entry) -> barCode.equals(entry.getValue().getProduct().getBarCode()))
                .findAny()
                .map(Map.Entry::getValue);
    }*/
}
