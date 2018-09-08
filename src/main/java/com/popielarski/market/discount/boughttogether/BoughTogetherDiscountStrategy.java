package com.popielarski.market.discount.boughttogether;

import com.popielarski.market.cart.Cart;
import com.popielarski.market.common.exception.LogicValidationException;
import com.popielarski.market.discount.DiscountStrategy;
import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.item.domain.Item;
import com.popielarski.market.product.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This discount provides decreasing by 50% total price of pair
 */

@Slf4j
public class BoughTogetherDiscountStrategy implements DiscountStrategy {
    private static final int MINIMAL_REQUIRED_AMOUNT_OF_PRODUCTS = 2;

    private BoughtTogetherDiscountRepository discountRepository;
    private ProductDiscountPairRepository itemDiscountPairRepository;

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

        Set<ProductDiscountPair> discountItems = items.stream()
                .map(Item::getProduct)
                .map(Product::getBoughtTogetherDiscount)
                .map(BoughtTogetherDiscount::getProductPairs)
                .flatMap(Set::stream)
                .filter((pair) -> pairExistsInCart(cart, pair))
                .collect(Collectors.toSet());


        Long cartTotalPrice = cart.getTotalPriceOfItems();
        TemporaryDiscountHolder temporaryDiscountHolder = new TemporaryDiscountHolder();

        discountItems
                .forEach((pair) -> {
                    temporaryDiscountHolder.increase(decreaseHalfAPrice(pair.getFirstProduct().getPrice(), pair.getSecondProduct().getPrice()));
                });

        cart.setFinalPrice(cartTotalPrice - temporaryDiscountHolder.getValueToDecrease());
        cart.applyDiscount(DiscountType.BOUGHT_TOGETHER);
        return cart;
    }

    private boolean pairExistsInCart(Cart cart, ProductDiscountPair pair) {
        return cart.getItems()
                .stream()
                .map(Item::getProduct)
                .filter(product -> product.equals(pair.getFirstProduct()) || product.equals(pair.getSecondProduct()))
                .distinct()
                .count() > 1;
    }

    private Long decreaseHalfAPrice(Long firstProductPrice, Long secondProductPrice) {
        return firstProductPrice + secondProductPrice / 2;
    }

    private List<Item> checkPairInCart(Cart cart, Product firstItem, Product secondItem) {
        return cart.getItems()
                .stream()
                .filter((item) -> canBeDiscounted(firstItem, secondItem, item))
                .collect(Collectors.toList());
    }

    private boolean canBeDiscounted(Product firstItem, Product secondItem, Item item) {
        return (item.getProduct().equals(firstItem) || item.getProduct().equals(secondItem));
    }

    @Getter
    @Setter
    class TemporaryDiscountHolder {
        private Long valueToDecrease;

        TemporaryDiscountHolder() {
            valueToDecrease = 0L;
        }

        void increase(Long valueToDecrease) {
            this.valueToDecrease += valueToDecrease;
        }
    }

}
