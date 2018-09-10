package com.popielarski.market.discount.domain;

import com.popielarski.market.cart.Cart;
import com.popielarski.market.cart.CartRepository;
import com.popielarski.market.common.exception.LogicValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiscountFacade {

    private final DiscountStrategyFactory discountStrategyFactory;
    private final DiscountMapper discountMapper;
    private final CartRepository cartRepository;

    public DiscountDTO assignMultiItemsDiscount(Long cartId) {
        log.info("Applying 'Multi Items' discount for cart with id: {}", cartId);
        return applyDiscount(cartId, DiscountType.MULTI_ITEMS);
    }

    public DiscountDTO assignBoughtTogetherDiscount(Long cartId) {
        log.info("Applying 'Bought Together' discount for cart with id: {}", cartId);
        return applyDiscount(cartId, DiscountType.BOUGHT_TOGETHER);
    }

    private DiscountDTO applyDiscount(Long cartId, DiscountType discountType) {
        Cart cartBeforeDiscount = cartRepository.findById(cartId)
                .orElseThrow(() -> new LogicValidationException(String.format("Cart with id %d does not exist", cartId)));
        log.debug("Cart with id %d has been found", cartId);

        validateCart(cartBeforeDiscount);
        DiscountStrategy discountStrategy = discountStrategyFactory.getDiscountStrategy(discountType);

        Cart cartAfterDiscount = discountStrategy.calculateDiscount(cartBeforeDiscount);
        cartRepository.save(cartAfterDiscount);
        log.debug("After discount: {}", cartBeforeDiscount);

        return discountMapper.toDiscountDTO(cartBeforeDiscount.getTotalPriceOfItems().toValue(),
                cartAfterDiscount.getFinalPrice().toValue(),
                discountType);
    }

    private void validateCart(Cart cartBeforeDiscount) {
        if (cartBeforeDiscount.isDiscountApplied()) {
            throw new LogicValidationException(String.format("Cart with id %d has already applied discount. " +
                    "One cart might contains only one discount.", cartBeforeDiscount.getId()));
        }

        if (cartBeforeDiscount.isPaid()) {
            throw new LogicValidationException(String.format("Cart with id %d has been already paid.",
                    cartBeforeDiscount.getId()));
        }

        if (CollectionUtils.isEmpty(cartBeforeDiscount.getItems())) {
            throw new LogicValidationException(String.format("Cart with id %d does not contain items.",
                    cartBeforeDiscount.getId()));
        }
    }
}
