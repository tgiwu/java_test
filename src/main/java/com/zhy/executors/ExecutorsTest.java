package com.zhy.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsTest {
    public static void main(String[] args){
        System.out.println("print it!!");
        //LinkedBlockingQueue
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //SynchronousQueue
        ExecutorService executorService1 = Executors.newCachedThreadPool();

        //DelayedWorkQueue
        ExecutorService executorService2 = Executors.newScheduledThreadPool(4);

        //LinkedBlockingQueue
        ExecutorService executorService3 = Executors.newFixedThreadPool(4);
    }

    public void testExecutor() {

    }
}