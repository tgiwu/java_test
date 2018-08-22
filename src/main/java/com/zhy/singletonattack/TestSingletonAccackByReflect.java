package com.zhy.singletonattack;

import java.lang.reflect.Constructor;

public class TestSingletonAccackByReflect {
    public static void main(String[] args) {
        try {
            Class<SingletonNotAttackByReflect> classType = SingletonNotAttackByReflect.class;
            Constructor<SingletonNotAttackByReflect> constructor = classType.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            SingletonNotAttackByReflect singleton = (SingletonNotAttackByReflect) constructor.newInstance();
            System.out.println("1: " + (null != singleton));
            SingletonNotAttackByReflect singleton1 = SingletonNotAttackByReflect.getInstance();
            System.out.println("2: " + (null != singleton1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
