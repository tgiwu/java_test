package com.zhy.productor_and_consumer.with_blockingDeque;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

public class Consumer extends Thread {
    private LinkedBlockingDeque<Integer> queue;
    String name;
    int maxSize;

    public Consumer(String name, LinkedBlockingDeque<Integer> queue, int maxSize) {
        super(name);
        this.name = name;
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while(true) {
            try {
                int x = queue.take();
                System.out.println("[" + name + "] Consuming : " + x);
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
