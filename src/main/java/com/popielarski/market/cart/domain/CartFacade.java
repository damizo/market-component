package com.popielarski.market.cart.domain;


import com.popielarski.market.cart.CartDTO;
import com.popielarski.market.item.domain.ItemDTO;
import com.popielarski.market.item.domain.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class CartFacade {

    private final ItemRepository itemRepository;
    private final CartSessionComponent cartSessionComponent;

    public CartDTO addItemToCart(Long itemDTO, Long itemId) {
        return null;
    }

    public InitializedCartDTO initializeCart() {
        return null;
    }
}
