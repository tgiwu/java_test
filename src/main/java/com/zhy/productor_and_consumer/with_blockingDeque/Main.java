package com.zhy.productor_and_consumer.with_blockingDeque;

import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    private static final int CAPACITY = 5;
    public static void main(String[] args) {
        LinkedBlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>(CAPACITY);
        Thread p1 = new Producer("pWithDeque-1", blockingDeque, CAPACITY);
        Thread p2 = new Producer("pWithDeque-2", blockingDeque, CAPACITY);
        Thread c1 = new Consumer("cWithDeque-1", blockingDeque, CAPACITY);
        Thread c2 = new Consumer("cWithDeque-2", blockingDeque, CAPACITY);
        Thread c3 = new Consumer("cWithDeque-3", blockingDeque, CAPACITY);
        Thread c4 = new Consumer("cWithDeque-4", blockingDeque, CAPACITY);

        p1.start();
        p2.start();
        c1.start();
        c2.start();
        c3.start();
        c4.start();
    }
}
