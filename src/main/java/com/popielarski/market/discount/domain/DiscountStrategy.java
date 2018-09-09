package com.popielarski.market.discount.domain;

import com.popielarski.market.cart.Cart;

public interface DiscountStrategy {
    Cart calculateDiscount(Cart cart);
}
