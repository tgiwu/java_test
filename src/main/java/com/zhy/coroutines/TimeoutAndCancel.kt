package com.zhy.coroutines

import kotlinx.coroutines.*

fun main() {

    with(TimeoutAndCancel()) {
        useIsActive()
        runNonCancellable()
        timeoutWithException()
        timeoutWithNull()
    }
}
class TimeoutAndCancel {

    fun useIsActive() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${++i}")
                    nextPrintTime += 500
                }
            }
        }
        delay(1_300)
        println("main: I'm tired of waiting")
        job.cancelAndJoin()
        println("main: Now I can quit")
    }

    fun runNonCancellable() = runBlocking {
        val job = launch {
            try {
                repeat(1_000) {
                    println("job: I'm sleep $it")
                    delay(500)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job:I'm running finally")
                    delay(1_000)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1_300)
        println("main: I'm tired of waiting")
        job.cancelAndJoin()
        println("main: Now I can quit")
    }

    fun timeoutWithException() = runBlocking {
        try {
            withTimeout(5_000) {
                repeat(1_000) {
                    println("I am sleeping $it")
                    delay(500)
                }
            }
        } catch (e :CancellationException) {
            e.printStackTrace()
        }
    }

    fun timeoutWithNull() = runBlocking {
        val result = withTimeoutOrNull(10000) {
            repeat(1000) {
                println("I am sleeping $it")
                delay(500)
            }
        }
        println("Result is $result")
    }
}