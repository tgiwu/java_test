package com.zhy.rxjava

import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun main(args: Array<String>) {
    val myTest = RxjavaTest()

    myTest.tryFlowable()
//    myTest.commonUse()
//    myTest.concurrencyUse()
//    myTest.parallelUse()
}
class RxjavaTest {
    fun tryFlowable() {
        Flowable.just("hello world").subscribe(System.out::println)
        Flowable.just("hello consumer").subscribe({ }, { }, { })
    }

    fun commonUse() {
        //每一步生成一个新的Flowable
        Flowable.fromCallable {
            Thread.sleep(1000)
            "awake"
        }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace)
        Thread.sleep(2000)
    }

    fun concurrencyUse() {
        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map { t -> t * t }
                .blockingSubscribe(System.out::println)
    }

    fun parallelUse() {
        Flowable.range(11, 20)
                .parallel()
                .runOn(Schedulers.computation())
                .map { v -> v * v }
                .sequential()
                .blockingSubscribe(System.out::println)
    }
}

