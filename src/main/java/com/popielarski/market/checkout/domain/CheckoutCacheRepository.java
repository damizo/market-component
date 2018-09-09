package com.popielarski.market.checkout.domain;


import com.popielarski.market.cart.Cart;
import com.popielarski.market.common.domain.InMemoryRepository;
import com.popielarski.market.item.Item;

import java.util.*;


class CheckoutCacheRepository extends InMemoryRepository<Cart> {

    public Optional<Item> findItemByCheckoutNumberAndCode(Integer checkoutNumber, String barCode) {
        return map.entrySet()
                .stream()
                .filter((entry) -> entry.getKey().equals(Long.valueOf(checkoutNumber)))
                .map(Map.Entry::getValue)
                .map(Cart::getItems)
                .flatMap(Collection::stream)
                .filter((item) -> barCode.equals(item.getProduct().getBarCode()))
                .findAny();
    }

    public void save(Integer checkoutNumber, Cart cart) {
        super.save(asLong(checkoutNumber), cart);
    }

    public Optional<Cart> findByCheckoutNumber(Integer checkoutNumber) {
        Optional<Map.Entry<Long, Cart>> entryOptional = this.map.entrySet()
                .stream()
                .filter((entry) -> entry.getKey().equals(asLong(checkoutNumber)))
                .findAny();

        if (entryOptional.isPresent()) {
            return Optional.ofNullable(entryOptional.get().getValue());
        }

        return Optional.empty();
    }

    private Long asLong(Integer value){
        return Long.valueOf(value);
    }
}
