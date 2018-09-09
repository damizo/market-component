package com.popielarski.market.common.utils;

import com.popielarski.market.common.domain.PriceDTO;

public class PriceUtils {

    public static boolean isCovered(PriceDTO priceToCover, Integer amount) {
        return amount >= priceToCover.getValue().intValue();
    }
}
