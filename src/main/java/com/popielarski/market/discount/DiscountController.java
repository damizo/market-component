package com.popielarski.market.discount;


import com.popielarski.market.discount.domain.DiscountDTO;
import com.popielarski.market.discount.domain.DiscountFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@Slf4j
class DiscountController {

    private final DiscountFacade discountFacade;

    @PutMapping(value = "/boughtTogether/carts/{cartId}")
    public DiscountDTO assignBoughtTogetherDiscount(@PathVariable Long cartId) {
        return discountFacade.assignBoughtTogetherDiscount(cartId);
    }

    @PutMapping(value = "/multiItems/carts/{cartId}")
    public DiscountDTO assignMultiItemsDiscount(@PathVariable Long cartId) {
        return discountFacade.assignMultiItemsDiscount(cartId);
    }

}
