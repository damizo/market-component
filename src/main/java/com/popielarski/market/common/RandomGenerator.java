package com.popielarski.market.common;

import java.util.Random;

public class RandomGenerator {

    public static Integer generateDecimal(int rangeFrom, int rangeTo){
        return new Random().ints(rangeFrom, rangeTo).findAny().getAsInt();
    }

}

