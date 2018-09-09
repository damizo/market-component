package com.popielarski.market.common.utils;

import com.popielarski.market.common.domain.PriceDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceUtils {

    public static boolean isCovered(PriceDTO priceToCover, Integer amount) {
        return amount >= priceToCover.getValue().intValue();
    }
}
