package com.zhy.productor_and_consumer.with_blockingDeque;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

public class Producer extends Thread {
    private LinkedBlockingDeque<Integer> blockingDeque;
    String name;
    int maxSize;
    int i = 0;

    public Producer(String name, LinkedBlockingDeque<Integer> queue, int maxSize) {
        super(name);
        this.blockingDeque = queue;
        this.name = name;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            try {
                blockingDeque.put(i);
                System.out.println("[" + name + "] Producing value: +" + i);
                i++;
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
