package com.zhy.coroutines

import com.zhy.extensions.log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import java.util.*

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
fun main(): Unit = with(SelectT()) {
     selectT()
    "====================".log()
    selectAorBT()
    "====================".log()
    selectWithOnSend()
    "====================".log()
    selectWithOnAwait()
    "====================".log()
    selectWithDelayOnAwait()
}

@ExperimentalCoroutinesApi
fun CoroutineScope.fizz() = produce {
    while (true) {
        delay(300)
        send("Fizz")
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.buzz() = produce {
    while (true) {
        delay(500)
        send("Buzz")
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.produceNumber(side: SendChannel<Int>) = produce<Int> {
    (1..10).forEach {
        delay(100)
        select {
            onSend(it){}
            side.onSend(it) {}
        }
    }
}

fun CoroutineScope.asyncString(time: Int, s : String = "Waited for $time ms") = async {
    delay(time.toLong())
    s
}

fun CoroutineScope.asyncStringList() : List<Deferred<String>> {
    val random = Random(3)
    return List(12) { asyncString(random.nextInt(10_000)) }
}

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun CoroutineScope.switchMapDeferred(input:ReceiveChannel<Deferred<String>>) = produce<String> {
    var current = input.receive()
    while (isActive) {
        val next = select<Deferred<String>?> {
            input.onReceiveOrNull {update ->
                update
            }

            current.onAwait {value ->
                send(value)
                input.receiveOrNull()
            }
        }
        if (next == null) {
            "Channel was closed".log()
            break
        } else {
            current = next
        }
    }
}

class SelectT {
    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    private suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz:ReceiveChannel<String>) {
        select<Unit> {
            fizz.onReceiveOrNull().invoke {
                "fizz -> $it".log()
            }

            buzz.onReceiveOrNull().invoke {
                "buzz -> $it".log()
            }
        }
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    fun selectT() = runBlocking {
        val fizz = fizz()
        val buzz = buzz()
        repeat(10) {
            selectFizzBuzz(fizz, buzz)
        }
//        different expression
//        repeat(10) {
//            selectFizzBuzz(fizz(), buzz())
//        }
        coroutineContext.cancelChildren()
    }


    @ExperimentalCoroutinesApi
    private suspend fun selectAorB(a:ReceiveChannel<String>, b:ReceiveChannel<String>) : String = select {
        a.onReceiveOrNull().invoke {
            if (null == it)
                "Channel a is clossed"
            else
                "a -> $it"
        }

        b.onReceiveOrNull().invoke {
            if (null == it)
                "Channel b is closed"
            else
                "b -> $it"
        }
    }

    /*select like to choose first produce*/
    @ExperimentalCoroutinesApi
    fun selectAorBT() = runBlocking {
        val a = produce {
            repeat(4) {send("hello $it")}
        }

        val b = produce {
            repeat(4) {send("World $it")}
        }

        repeat(8) {
            selectAorB(a, b).log()
        }

        coroutineContext.cancelChildren()
    }

    @ExperimentalCoroutinesApi
    fun selectWithOnSend() = runBlocking {
        val side = Channel<Int>()
        launch {
            side.consumeEach { "Side channel has $it".log() }
        }

        produceNumber(side).consumeEach {
            "Consuming $it".log()
            delay(250)
        }

        "Done consuming".log()
        coroutineContext.cancelChildren()
    }

    fun selectWithOnAwait() = runBlocking {
        val list = asyncStringList()
        val result = select<String> {
            list.withIndex().forEach { (index, deferred) ->
                deferred.onAwait { answer ->
                    "Deferred $index produced answer $answer"}
            }
        }

        result.log()
        val countActive = list.count { it.isActive }
        "$countActive coroutine are still active".log()
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun selectWithDelayOnAwait() = runBlocking {
        val channel = Channel<Deferred<String>>()

        launch {
            for (s in switchMapDeferred(channel)) {
                s.log()
            }
        }
        channel.send(asyncString(100, "BEGIN"))
        delay(200)
        channel.send(asyncString(500, "SLOW"))
        delay(100)
        channel.send(asyncString(100,"REPLACE"))
        delay(500)
        channel.send(asyncString(500, "END"))
        delay(1000)
        channel.close()
        delay(500)
    }
}