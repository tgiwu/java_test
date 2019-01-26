package com.zhy.thread;

public class TestThread {

    TestThread(){
        System.out.println("constractor");
    }

    static {
        System.out.println("static");
    }

    public static void main(String[] args) {

        new TestThread();

        new Thread(() -> System.out.println("runnable")) {
            @Override
            public void run() {
                super.run();
                System.out.println("thread run");
            }
        }.start();
    }
}
