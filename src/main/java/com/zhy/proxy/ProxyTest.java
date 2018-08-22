package com.zhy.proxy;

public class ProxyTest implements Person {

    private Person o;

    public ProxyTest(Person o) {
        this.o = o;
    }

    @Override
    public void sayHello(String content, int age) {
        o.sayHello(content, age);
    }

    @Override
    public void sayGoodbye(boolean seeAgain, double time) {
        o.sayGoodbye(true, System.currentTimeMillis());
    }

    public static void main(String[] args) {
        Student student = new Student();
        ProxyTest proxyTest = new ProxyTest(student);
        proxyTest.sayHello("content", 22);
        System.out.println("-----------------------------------");
        proxyTest.sayGoodbye(true, System.currentTimeMillis());
    }
}
