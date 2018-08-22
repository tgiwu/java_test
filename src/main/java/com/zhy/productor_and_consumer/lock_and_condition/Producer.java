package com.zhy.productor_and_consumer.lock_and_condition;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Producer extends Thread {
    private Queue<Integer> queue;
    String name;
    int maxSize;
    int i = 0;
    ReentrantLock lock;
    Condition fullCondition, emptyCondition;

    public Producer(String name, Queue<Integer> queue, int maxSize, ReentrantLock lock, Condition fullCondition, Condition emptyCondition) {
        super(name);
        this.name = name;
        this.queue = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.fullCondition = fullCondition;
        this.emptyCondition = emptyCondition;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            while (queue.size() == maxSize) {
                try {
                    System.out.println("queue is full, Producer[" + name +"] thread waiting for consumer to take something from queue" );
                    fullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("[" + name + "] Producing value :  + " + i);
                queue.offer(i++);
                fullCondition.signalAll();
                emptyCondition.signalAll();

                lock.unlock();

                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
