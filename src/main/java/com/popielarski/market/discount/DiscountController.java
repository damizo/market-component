package com.popielarski.market.discount;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    private final DiscountFacade discountFacade;

    @PutMapping(value = "/boughtTogether/carts/{cartId}")
    public DiscountDTO addBoughtTogetherDiscount(@PathVariable Long cartId) {
        return discountFacade.addBoughtTogetherDiscount(cartId);
    }

    @PutMapping(value = "/multiItems/carts/{cartId}")
    public DiscountDTO addMultiItemsDiscount(@PathVariable Long cartId) {
        return discountFacade.addMultiItemsDiscount(cartId);
    }
}
