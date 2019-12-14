package com.zhy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Clock clock = Clock.systemDefaultZone();
        long mills = clock.millis();
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);

        NumberFormat decimalFormat = new DecimalFormat("000");
        long current = 1481704241039l;
        current = current / 1000 * 1000;
        System.out.println(current);


        Integer a = 1;
        Integer b = 2;
        System.out.println(" a = " + a + " b = " + b);
        swap(a, b);
        System.out.println(" a = " + a + " b = " + b);
    }
    private static void swap(Integer a, Integer b) {

        int temp = a;
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            field.setInt(a, b);
            field.setInt(b, temp);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void reflect() {
        try {
            Class<?> clazz = Class.forName("java.lang.String");
            Class<?> instance = (Class<?>) clazz.newInstance();
            Method method = clazz.getMethod("replace", String.class, String.class);
            Object o = method.invoke(instance, "a", "b");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
