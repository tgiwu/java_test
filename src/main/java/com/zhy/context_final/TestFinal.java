package com.zhy.context_final;

public class TestFinal {
    public static void main(String[] args){
        TestFinal testFinal = new TestFinal();
        testFinal.testFinalArray();
    }

    private void testFinalArray() {
        final int[] finalArray = {1, 2, 3, 4, 5, 6, 7, 8};
        for (int a : finalArray) {
            System.out.print(a + "," );
        }

        System.out.print("\n");

        finalArray[1] = 9;
        finalArray[4] = 10;

        for (int a : finalArray) {
            System.out.print(a + "," );
        }
        System.out.print("\n");

        //It will be a error cannot modify this array, you can only modify element
        //finalArray = new int[8];
    }
}
