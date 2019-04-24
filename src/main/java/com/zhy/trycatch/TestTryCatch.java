package com.zhy.trycatch;

public class TestTryCatch {
    public static void main(String[] args) {
        System.out.println(new TestTryCatch().testTryReturn());
    }

    public int testTryReturn() {
        try {
//            throw new Exception("1");
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            System.out.println("It's finally");
//            return 3;
        }
    }
}
