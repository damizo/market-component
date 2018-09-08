package com.popielarski.market.discount;

import com.popielarski.market.cart.Cart;

public interface DiscountStrategy {
    Cart calculateDiscount(Cart cart);
}
