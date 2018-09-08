package com.popielarski.market.common;

public class PriceUtils {

    public static boolean isCovered(Long priceToCover, Integer amount){
        return amount >= priceToCover;
    }
}
