package com.zhy.singleton;

public class SingletonWithEnum {

    public SingletonWithEnum(){}

    public static SingletonWithEnum getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private static enum Singleton {
        INSTANCE;
        private SingletonWithEnum singleton;
        private Singleton() {
            singleton = new SingletonWithEnum();
        }

        public SingletonWithEnum getInstance() {
            return singleton;
        }
    }

    public static void main(String[] args) {
        SingletonWithEnum obj1 = SingletonWithEnum.getInstance();
        SingletonWithEnum obj2 = SingletonWithEnum.getInstance();

        System.out.println(obj1.equals(obj2));
    }
}
