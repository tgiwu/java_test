package com.zhy.productor_and_consumer;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    private static final int CAPACITY = 5;

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        Thread p1 = new Producer("p-1", queue, CAPACITY);
        Thread p2 = new Producer("p-2", queue, CAPACITY);

        Thread c1 = new Consumer("c-1", queue, CAPACITY);
        Thread c2 = new Consumer("c-2", queue, CAPACITY);
        Thread c3 = new Consumer("c-3", queue, CAPACITY);

        p1.start();
        p2.start();
        c1.start();
        c2.start();
        c3.start();
    }
}
