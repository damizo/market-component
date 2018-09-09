package com.popielarski.market.discount.domain.boughttogether;

import com.popielarski.market.cart.Cart;
import com.popielarski.market.common.domain.Calculator;
import com.popielarski.market.common.exception.LogicValidationException;
import com.popielarski.market.discount.domain.DiscountStrategy;
import com.popielarski.market.discount.domain.DiscountType;
import com.popielarski.market.item.Item;
import com.popielarski.market.product.domain.Price;
import com.popielarski.market.product.domain.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * This discount provides decreasing by 30% total price of pair
 */

@Slf4j
public class BoughTogetherDiscountStrategy implements DiscountStrategy {

    @Override
    public Cart calculateDiscount(Cart cart) {
        Set<Item> items = cart.getItems()
                .stream()
                .filter((item -> item.getProduct().hasBoughtTogetherDiscount()))
                .collect(Collectors.toSet());
        log.info("Items to discount with 'Bought Together' discount: {}", items);

        if (items.isEmpty()) {
            throw new LogicValidationException(String.format("Cart with id %d does not contain items that are allowed to discount", cart.getId()));
        }

        Set<ProductDiscountPair> discountItems = getExistingPairsFromCart(cart, items);
        Price cartTotalPrice = cart.getTotalPriceOfItems();
        TemporaryDiscountHolder temporaryDiscountHolder = new TemporaryDiscountHolder();

        discountItems
                .forEach(pair -> {
                    temporaryDiscountHolder.increase(decreaseHalfAPrice(pair.getFirstProduct().getPrice(),
                            pair.getSecondProduct().getPrice()));
                });

        cart.setFinalPrice(Calculator.subtract(cartTotalPrice, temporaryDiscountHolder.getValueToDecrease()));
        cart.applyDiscount(DiscountType.BOUGHT_TOGETHER);
        return cart;
    }

    private Set<ProductDiscountPair> getExistingPairsFromCart(Cart cart, Set<Item> items) {
        return items.stream()
                .map(Item::getProduct)
                .map(Product::getBoughtTogetherDiscount)
                .map(BoughtTogetherDiscount::getProductPairs)
                .flatMap(Set::stream)
                .filter(pair -> pairExistsInCart(cart, pair))
                .collect(Collectors.toSet());
    }

    private boolean pairExistsInCart(Cart cart, ProductDiscountPair pair) {
        return cart.getItems()
                .stream()
                .map(Item::getProduct)
                .filter(product -> product.equals(pair.getFirstProduct()) || product.equals(pair.getSecondProduct()))
                .distinct()
                .count() > 1;
    }

    private Price decreaseHalfAPrice(Price firstProductPrice, Price secondProductPrice) {
        Price result = Calculator.add(firstProductPrice, secondProductPrice);
        return Calculator.multiple(result, "0.30");
    }

    @Getter
    @Setter
    class TemporaryDiscountHolder {
        private Price valueToDecrease;

        TemporaryDiscountHolder() {
            valueToDecrease = Price.zero();
        }

        void increase(Price valueToDecrease) {
            this.valueToDecrease = Calculator.add(this.valueToDecrease, valueToDecrease);
        }
    }

}
