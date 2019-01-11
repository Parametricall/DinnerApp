package com.parametricall.dinner;

import java.util.Random;

class RandomDinnerSelector {

    Integer generateRandomNumber(int dbSize) {
        Random random = new Random();
        return random.nextInt(dbSize);
    }
}
