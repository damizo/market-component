package com.popielarski.market.repository;

import com.popielarski.market.cart.Cart;
import com.popielarski.market.cart.CartRepository;
import com.popielarski.market.common.domain.InMemoryRepository;

public class CartInMemoryRepository extends InMemoryRepository<Cart> implements CartRepository{

}
