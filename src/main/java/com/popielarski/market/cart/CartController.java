package com.popielarski.market.cart;


import com.popielarski.market.cart.domain.CartFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartFacade cartFacade;
/*
    @PostMapping
    InitializedCartDTO takeNewCart(){
        log.debug("Taking new cart...");
        return cartFacade.initializeCart();
    }*/

  /*  @PutMapping(value = "/{cartId}/carts/{itemId}")
    CartDTO addItemToCart(@PathVariable Long cartId, @PathVariable Long itemId){
        log.debug("Add to cart item with id: {}", cartId, itemId);
        return cartFacade.addItemToCart(cartId, itemId);
    }*/

}
