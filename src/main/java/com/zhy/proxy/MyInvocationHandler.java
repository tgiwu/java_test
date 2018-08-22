package com.zhy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {
    private Object object;

    public MyInvocationHandler(Object o) {
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(MyInvocationHandler.class.getSimpleName() + " invoke begin");
        System.out.println("com.zhy.proxy: " + proxy.getClass().getName());
        System.out.println("method: " + method.getName());
        for (Object o : args) {
            System.out.println("arg: " + o);
        }
        method.invoke(object, args);
        System.out.println(MyInvocationHandler.class.getSimpleName() + " invoke end");
        return null;
    }

    public static void main(String[] args) {
        Student student = new Student();
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?>[] interfaces = student.getClass().getInterfaces();
        MyInvocationHandler handler = new MyInvocationHandler(student);
        Person proxy = (Person) Proxy.newProxyInstance(loader, interfaces, handler);
        proxy.sayHello("invocation hello", 1);
        proxy.sayGoodbye(false, 100);
        System.out.println("invocation invoke end");
    }
}
