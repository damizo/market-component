package com.popielarski.market.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.MathContext;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtils {
    public static final MathContext mathContext = new MathContext(4, RoundingMode.UP);
}
