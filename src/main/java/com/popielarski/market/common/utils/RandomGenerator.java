package com.popielarski.market.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomGenerator {

    public static Integer generateDecimal(int rangeFrom, int rangeTo) {
        return new Random().ints(rangeFrom, rangeTo).findAny().getAsInt();
    }

}

