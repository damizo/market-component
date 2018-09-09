package com.popielarski.market.discount.domain;

import com.popielarski.market.cart.Cart;
import com.popielarski.market.cart.CartRepository;
import com.popielarski.market.common.exception.LogicValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiscountFacade {

    private final DiscountStrategyFactory discountStrategyFactory;
    private final DiscountMapper discountMapper;
    private final CartRepository cartRepository;

    public DiscountDTO addMultiItemsDiscount(Long cartId) {
        Cart cartBeforeDiscount = cartRepository.findById(cartId)
                .orElseThrow(() -> new LogicValidationException(String.format("Cart with id %d does not exist", cartId)));
        log.debug("Cart with id %d has been found", cartId);

        DiscountStrategy discountStrategy = discountStrategyFactory.getDiscountStrategy(DiscountType.MULTI_ITEMS);

        Cart cartAfterDiscount = discountStrategy.calculateDiscount(cartBeforeDiscount);
        cartRepository.save(cartAfterDiscount);
        log.debug("After discount: {}", cartBeforeDiscount);

        return discountMapper.toDiscountDTO(cartBeforeDiscount.getTotalPriceOfItems().toValue(),
                cartAfterDiscount.getFinalPrice().toValue(),
                DiscountType.MULTI_ITEMS);
    }


    public DiscountDTO addBoughtTogetherDiscount(Long cartId) {
        Cart cartBeforeDiscount = cartRepository.findById(cartId)
                .orElseThrow(() -> new LogicValidationException(String.format("Cart with id %d does not exist", cartId)));
        log.debug("Cart with id %d has been found", cartId);

        DiscountStrategy discountStrategy = discountStrategyFactory.getDiscountStrategy(DiscountType.BOUGHT_TOGETHER);

        Cart cartAfterDiscount = discountStrategy.calculateDiscount(cartBeforeDiscount );
        cartRepository.save(cartAfterDiscount);
        log.debug("After discount: {}", cartBeforeDiscount);

        return discountMapper.toDiscountDTO(cartBeforeDiscount.getTotalPriceOfItems().toValue(),
                cartAfterDiscount.getFinalPrice().toValue(),
                DiscountType.BOUGHT_TOGETHER);
    }
}
