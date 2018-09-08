package com.popielarski.market.common;

import com.popielarski.market.common.domain.Value;

public class PriceUtils {

    public static boolean isCovered(Value priceToCover, Integer amount) {
        return amount >= priceToCover.getValue().intValue();
    }
}
