package com.zhy.coroutines

import com.zhy.extensions.log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    with(UseAsync()) {
        asyncNormal()
        "=====================".log()

        asyncLazy()
        "=====================".log()

        concurrentAsync()
        "=====================".log()

        differentThreadJump()
        "=====================".log()

        checkMyJob()
        "=====================".log()

        subCoroutine()
        "=====================".log()

        threadLocalT1()
        "=====================".log()

        threadLocalT2()
        "=====================".log()

        threadLocalT3()
        "=====================".log()

    }
}
class UseAsync {

    fun asyncNormal() = runBlocking {
        val time = measureTimeMillis {
            val one = async (CoroutineName("v1coroutine")) {doSomethingUsefulOne()}
            val two = async (CoroutineName("v2coroutine")) {doSomethingUsefulTwo()}
            "the answer is ${one.await() + two.await()}".log()
        }
        "Completed in $time ms".log()
    }

    fun asyncLazy() = runBlocking {
        "Completed in ${measureTimeMillis { 
            //只有调用await是才启动
            "the answer is ${async (start = CoroutineStart.LAZY) {doSomethingUsefulOne()}.await() + 
                    async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo()}.await()}".log()
        }} ms".log()
    }

    fun concurrentAsync() = runBlocking {
        val  time = measureTimeMillis {
            "The answer is ${concurrentSum()}".log()
        }
        "Completed in $time ms".log()
    }

    @ObsoleteCoroutinesApi
    fun differentThreadJump() = newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                "Start in ctx1".log()
                withContext(ctx2) {
                    "Working in ctx2".log()
                }
                "Back to ctx1".log()
            }
        }
    }

    fun checkMyJob() = runBlocking {
        "My job is ${coroutineContext[Job]}".log()
    }

    fun subCoroutine() = runBlocking {
        val request = launch(Dispatchers.Default + CoroutineName("mainCoroutine")) {
            "I'm working in thread ${Thread.currentThread().name}".log()
            GlobalScope.launch {
                "job1: I run in GlobalScope and Execute independently!".log()
                delay(1_000)
                "job1: I am not affected by cancellation of the request".log()
            }

            launch {
                delay(100)
                "job2: I am a child of the request coroutine".log()
                delay(1_000)
                "job2: I will not execute this line if my parent request is cancelled".log()
            }
        }
        delay(500)
        request.cancel()
        delay(1_000)
        "main: Who has survived request cancellation?"
    }

    fun threadLocalT1() {
        val myThreadLocal = ThreadLocal<String?>()

        runBlocking {

            "11 ${myThreadLocal.get()}".log()

            launch(Dispatchers.Default + myThreadLocal.asContextElement(value = "foo")) {
                "12 ${myThreadLocal.get()}".log()
                withContext(Dispatchers.IO) {
                    "13 ${myThreadLocal.get()}".log()
                }
            }

            "14 ${myThreadLocal.get()}".log()
        }
    }

    fun threadLocalT2() {
        val myThreadLocal = ThreadLocal<String?>()
        runBlocking {
            launch(Dispatchers.Default) {
                "21 ${myThreadLocal.get()}".log()
                myThreadLocal.set("default")
            }
            "22 ${myThreadLocal.get()}".log()
        }
    }

    fun threadLocalT3() {
        val tl = ThreadLocal.withInitial { "initial" }

        runBlocking {
            "31 ${tl.get()}".log()

            withContext(tl.asContextElement("modified")) {
                "32 ${tl.get()}".log()

            }
            "33 ${tl.get()}".log()

        }
    }

    private suspend fun concurrentSum() : Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }

    private suspend fun doSomethingUsefulOne(): Int {
        delay(1_000)
        "one".log()
        return 13
    }

    private suspend fun doSomethingUsefulTwo(): Int {
        delay(1_000)
        "two".log()
        return 29
    }
}