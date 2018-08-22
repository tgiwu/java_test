package com.zhy.singletonattack;

public class SingletonNotAttackByReflect {
    private static boolean flag = false;

    private SingletonNotAttackByReflect() {
        synchronized (SingletonNotAttackByReflect.class) {
            if (!flag) {
                flag = !flag;
            } else {
              throw new RuntimeException("under attack");
            }
        }
    }

    public static class SingletonHolder{
        private static final SingletonNotAttackByReflect INSTANCE = new SingletonNotAttackByReflect();
    }

    public static SingletonNotAttackByReflect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void main(String[] args) {

    }
}
