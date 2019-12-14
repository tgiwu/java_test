package com.zhy;

import cn.hutool.core.collection.CollUtil;

import java.util.*;

public class MyTest {
    public static void main(String[] args) {
//        new MyTest().doTest();


    }

    private void doTest(){
//        String saved = "1,2,3";
////        String[] l = new String[]{"1","2","3","4","5","6","7","8","9"};
//        String[] list = saved.split(",");
//        String current;
//        if (list.length == 0) {
//            current = "";
//        } else if (list.length <= 5) {
//            current = Arrays.toString(list);
//        } else {
//            StringBuilder builder = new StringBuilder();
//            for (int i = list.length - 5; i < list.length; i++) {
//                if (builder.length() != 0) builder.append(",");
//                builder.append(list[i]);
//            }
//            current = builder.toString();
//        }

        System.out.println("^");

//        List<TestData> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            list.add(new TestData());
//        }
//
//        System.out.println("origin " + list);
//        Set<TestData> set = CollUtil.newHashSet(list);
//        System.out.println("list to set " + set);
//
//        System.out.println("set to list " + CollUtil.list(false, set));

//        list.forEach(it ->{
//                it.property_0 = "0";
//                it.property_1 = "1";
//                it.property_2 = "2";});
//
//        list.forEach(it ->
//                System.out.println("it = " + it));
    }

    class TestData{
        String property_0 = "p0";
        String property_1 = "p1";
        String property_2 = "p2";

        @Override
        public String toString() {
            return "TestData{" +
                    "property_0='" + property_0 + '\'' +
                    ", property_1='" + property_1 + '\'' +
                    ", property_2='" + property_2 + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestData testData = (TestData) o;
            return Objects.equals(property_0, testData.property_0) &&
                    Objects.equals(property_1, testData.property_1) &&
                    Objects.equals(property_2, testData.property_2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(property_0, property_1, property_2);
        }
    }
}
