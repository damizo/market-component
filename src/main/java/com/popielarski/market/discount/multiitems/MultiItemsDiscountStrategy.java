package com.popielarski.market.discount.multiitems;

import com.popielarski.market.cart.Cart;
import com.popielarski.market.common.Calculator;
import com.popielarski.market.common.exception.LogicValidationException;
import com.popielarski.market.discount.DiscountStrategy;
import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.item.domain.Item;
import com.popielarski.market.item.domain.ItemRepository;
import com.popielarski.market.product.Price;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * This discount provides decreasing price of product to 20% on total price
 */
@RequiredArgsConstructor
@Slf4j
public class MultiItemsDiscountStrategy implements DiscountStrategy {

    private MultiItemsDiscountRepository discountRepository;
    private MultiItemsDiscountFactory multiItemsDiscountFactory;
    private ItemRepository itemRepository;

    @Override
    public Cart calculateDiscount(Cart cart) {
        Set<Item> items = cart.getItems()
                .stream()
                .filter((item -> item.getQuantity() > 1 && item.getProduct().hasMultiItemDiscount()))
                .collect(Collectors.toSet());
        log.info("Items to discount with 'Multi Item' discount: {}", items);

        if (items.isEmpty()) {
            throw new LogicValidationException(String.format("Cart with id %d does not contain items that are allowed to discount", cart.getId()));
        }

        Price cartTotalPrice = cart.getTotalPriceOfItems();
        TemporaryDiscountHolder temporaryDiscountHolder = new TemporaryDiscountHolder();

        items.stream()
                .map(Item::getTotalPrice)
                .forEach((price) -> {
                    Price result = discount(price);
                    temporaryDiscountHolder.increase(result);
                });

        cart.setFinalPrice(Calculator.subtract(cartTotalPrice, temporaryDiscountHolder.getValueToDecrease()));
        cart.applyDiscount(DiscountType.MULTI_ITEMS);
        return cart;
    }

    private Price discount(Price price) {
        return Calculator.multiple(price, "0.20");
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
