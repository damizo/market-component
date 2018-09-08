package com.popielarski.market.discount;

import com.popielarski.market.discount.boughttogether.BoughTogetherDiscountStrategy;
import com.popielarski.market.discount.multiitems.MultiItemsDiscountStrategy;

public class DiscountStrategyFactory {

    public DiscountStrategy getDiscountStrategy(DiscountType type) {
        DiscountStrategy discountStrategy = null;

        switch (type) {
            case BOUGHT_TOGETHER:
                discountStrategy = new BoughTogetherDiscountStrategy();
                break;
            case MULTI_ITEMS:
                discountStrategy = new MultiItemsDiscountStrategy();
                break;
            default:
                throw new UnsupportedOperationException(String.format("Discount type %s has not been handle", type.name()));
        }

        return discountStrategy;
    }
}
