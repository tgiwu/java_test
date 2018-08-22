package com.zhy.productor_and_consumer.lock_and_condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final int CAPACITY = 5;
    private static final ReentrantLock lock = new ReentrantLock();
    private static  final Condition fullCondition = lock.newCondition();
    private static final Condition emptyCondition = lock.newCondition();

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();

        Thread p1 = new Producer("pWithLock-1", queue, CAPACITY, lock, fullCondition, emptyCondition);
        Thread p2 = new Producer("pWithLock-2", queue, CAPACITY, lock, fullCondition, emptyCondition);
        Thread c1 = new Comsumer("cWithLock-1", queue, CAPACITY, lock, fullCondition, emptyCondition);
        Thread c2 = new Comsumer("cWithLock-2", queue, CAPACITY, lock, fullCondition, emptyCondition);
        Thread c3 = new Comsumer("cWithLock-3", queue, CAPACITY, lock, fullCondition, emptyCondition);

        p1.start();
        p2.start();
        c1.start();
        c2.start();
        c3.start();
    }
}
