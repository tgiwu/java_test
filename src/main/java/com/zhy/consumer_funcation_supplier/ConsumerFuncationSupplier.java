package com.zhy.consumer_funcation_supplier;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ConsumerFuncationSupplier {
    public static void main(String[] args) {
        ConsumerFuncationSupplier main = new ConsumerFuncationSupplier();
        main.testFuncation();
        main.testConsumer();
        main.testOptional();
    }

    private void testOptional() {
        Optional<String> optional = Optional.of("bam");
        System.out.println(optional.isPresent());
        System.out.println(optional.get());
        System.out.println(optional.orElse("fallback"));
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));
    }

    private void testConsumer() {
        Consumer<Person> greeter = (p) -> System.out.println("hello " + p.firstName);
        greeter.accept(new ConsumerFuncationSupplier().doTest());

    }
    private void testFuncation() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        System.out.println(toInteger.apply("123"));
        System.out.println(backToString.apply("345"));
    }

    private Person doTest() {
        Supplier<Person> personSupplier = Person::new;
        Person person = personSupplier.get();
        PersonFactory<Person> personFactory = Person::new;
        System.out.print(personFactory.create("peter", "parker").toString());
        return personFactory.create("abc", "cba");
    }

    class Person {
        String firstName, lastName;

        Person() {}

        Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {

            return "Person{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }
    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }
}
