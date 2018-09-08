package com.popielarski.market.cart.domain;

import com.popielarski.market.cart.CartDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.concurrent.ConcurrentHashMap;


@Component
@SessionScope
@Data
class CartSessionComponent {

    private ConcurrentHashMap<String, CartDTO> carts = new ConcurrentHashMap<>();

}
