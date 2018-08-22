package com.zhy.teststream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TestStream {
    public static void main(String[] args) {
        TestStream testStream = new TestStream();
        List<String> list = testStream.buildStream();

        testStream.testFilter(list);
        testStream.testSort(list);
        testStream.testMap(list);
        testStream.testMatch(list);
        testStream.testCount(list);
        testStream.testReduce(list);


        List<String> strings = testStream.buildValues(1_000_000);
        testStream.testSingleStreams(strings);
        testStream.testAsyncStreams(strings);
    }

    private List<String> buildStream() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        return stringCollection;
    }

    private void testMap(List<String> stream) {
        System.out.println("test map");
        stream.stream().map(String::toUpperCase).sorted((a,b) -> b.compareTo(a)).forEach(System.out::println);
    }

    private void testSort(List<String> stream) {
        System.out.println("test sort");
        stream.stream().sorted().filter((s) -> s.startsWith("a")).forEach(System.out::println);
    }

    private void testFilter(List<String> stream) {
        System.out.println("test filter");
        stream.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);
    }

    private void testReduce(List<String> stream) {
        Optional<String> reduced = stream.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
    }

    private void testCount(List<String> stream) {
        System.out.println("start with b count " + stream.stream().filter(s -> s.startsWith("b")).count());
    }

    private void testMatch(List<String> stream) {
        System.out.println("test match");
        boolean anyStartsWithA = stream.stream().anyMatch((s) -> s.startsWith("a"));
        System.out.println(("start with a " + anyStartsWithA));
        boolean allStartsWithA = stream.stream().allMatch(s -> s.startsWith("a"));
        System.out.println(("all start with a " + allStartsWithA));
        boolean noneStartsWithA = stream.stream().noneMatch(s -> s.startsWith("a"));
        System.out.println(("none start with a " + noneStartsWithA));
    }


    private List<String> buildValues(int max) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        return values;
    }

    private void testSingleStreams(List<String> values) {
        System.out.println("test simgle");
        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long mills = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms ", mills));
    }

    private void testAsyncStreams(List<String> values) {
        System.out.println("test async");
        long t0 = System.nanoTime();
        long count = values.parallelStream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long mills = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms ", mills));
    }
}
