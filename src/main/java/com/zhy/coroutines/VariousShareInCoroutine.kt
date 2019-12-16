package com.zhy.coroutines

import com.zhy.extensions.log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

@ObsoleteCoroutinesApi
fun main(): Unit = with(VariousShareInCoroutine()) {
    withAtomic()
    "=================".log()
    withSingleThreadContextSlender()
    "=================".log()
    withSingleThreadContextThick()
    "=================".log()
    mutualExclusion()
    "=================".log()
    withActor()

}

sealed class CounterMsg
object  IncCounter: CounterMsg()
class GetCounter(val response:CompletableDeferred<Int>) : CounterMsg()

fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0
    for (msg in channel) {
        when(msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}

/*volatile is invalid，cause volatile guarantee read and write atomically，but when many actions is happening , it's useless*/
class VariousShareInCoroutine {

    private suspend fun massiveRun(action : suspend () -> Unit) {
        val n = 100
        val k = 1_000
        val time = measureTimeMillis {
            coroutineScope {
                repeat(n) {
                    launch {
                        repeat(k) {
                            action()
                        }
                    }
                }            }
        }

        "Completed is ${n * k} actions in $time ms"
    }


    fun withAtomic() = runBlocking {
        var counter = AtomicInteger()
        withContext(Dispatchers.Default) {
            massiveRun {
                counter.incrementAndGet()
            }
        }
        "atomic counter = $counter".log()
    }

    @ObsoleteCoroutinesApi
    fun withSingleThreadContextSlender() {
        val counterContext = newSingleThreadContext("CounterContext")
        var counter = 0

        runBlocking {
            withContext(Dispatchers.Default) {
                massiveRun {
                    withContext(counterContext) {
                        counter++
                    }
                }
            }
            "slender counter = $counter".log()
        }
    }

    @ObsoleteCoroutinesApi
    fun withSingleThreadContextThick() {
        val counterContext = newSingleThreadContext("CounterContext")
        var counter = 0

        runBlocking {
            with(counterContext) {
                massiveRun {
                    counter++
                }
            }
            "thick counter = $counter".log()

        }
    }

    fun mutualExclusion() {
        val mutex = Mutex()
        var counter = 0
        runBlocking {
            withContext(Dispatchers.Default) {
                massiveRun {
                    mutex.withLock {
                        counter++
                    }
                }
            }
            "mutex counter = $counter".log()
        }
    }

    //an actor is a double produce channel
    fun withActor() = runBlocking {
        val counter = counterActor()
        withContext(Dispatchers.Default) {
            massiveRun {
                counter.send(IncCounter)
            }
        }

        val response = CompletableDeferred<Int>()
        counter.send(GetCounter(response))
        "Actor counter = ${response.await()}".log()
        counter.close()
    }
}