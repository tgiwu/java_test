package com.zhy.object;

public class TestObject {
    public static void main(String[] args) {
        Object[] arr = {"abc", 'c', 2, 3.0f};
        for (Object o : arr) {
            System.out.println("type : " + o.getClass());
        }
    }
}
