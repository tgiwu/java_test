package com.zhy.coroutines

import com.zhy.extensions.log
import kotlinx.coroutines.*
import java.lang.ArithmeticException
import java.lang.AssertionError
import java.lang.IndexOutOfBoundsException

fun main() {
    with(CoroutineHandleException()) {
        exceptionSpread()
        "==================".log()
        withHandle()
        "==================".log()
        cancelException()
        "==================".log()
        cancelByException()
    }
}
class CoroutineHandleException {

    fun exceptionSpread() = runBlocking {
        val job = GlobalScope.launch {
            "Throwing exception from launch".log()
            throw IndexOutOfBoundsException()
        }
        job.join()
        "Joined failed job".log()
        val deferred = GlobalScope.async {
            "Throwing exception from async".log()
            throw ArithmeticException()
        }
        try {
            deferred.await()
            "Unreached".log()
        } catch (e : ArithmeticException) {
            "Caught ArithmeticException".log()
        }
    }

    fun withHandle() = runBlocking {
        val handle = CoroutineExceptionHandler { coroutineContext, throwable ->
            "Caught $throwable".log()
        }

        val job = GlobalScope.launch(handle) {
            throw AssertionError()
        }

        //invalid
        val deferred = GlobalScope.async(handle) {
            throw ArithmeticException()
        }

        joinAll(job, deferred)
    }

    //cancel coroutine by CancellationException inner,it will be ignored by any handle.
    //when a child coroutine be cancelled by Job.cancel, the parent is not terminated.
    fun cancelException() = runBlocking {
        val job = launch {
            val child = launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    "Child is cancelled".log()
                }
            }
            yield()

            "Cancelling child".log()

            child.cancel()
            child.join()
            yield()
            "Parent is not cancelled".log()
        }
        job.join()
    }

    fun cancelByException() = runBlocking {
        val handle = CoroutineExceptionHandler{_, exception ->
            "Caught $exception".log()
        }

        val job = GlobalScope.launch(handle) {
            launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    withContext(NonCancellable) {
                        "Children are cancelled, but exception is not handled until all children terminate".log()
                        delay(100)
                        "The first child finished its non cancellable block".log()
                    }
                }
            }
            launch {
                delay(10)
                "Second child throws an exception".log()
                throw ArithmeticException()
            }
        }
        job.join()
    }
}