package com.zhy.proxy;

public class Student implements Person {
    @Override
    public void sayHello(String content, int age) {
        System.out.println("Hello " + content + " " + age);
    }

    @Override
    public void sayGoodbye(boolean seeAgain, double time) {
        System.out.println("goodBye " + time + " " + seeAgain);
    }
}
