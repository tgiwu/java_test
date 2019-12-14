package com.zhy.coroutines

import com.zhy.extensions.log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception

@ExperimentalCoroutinesApi
@FlowPreview
fun main() {
    with(ConcurrentStream()) {
        consumeSequence()
        "===================".log()
        collectFlow()
        "===================".log()
        mapFlow()
        "===================".log()
        transformFlow()
        "===================".log()
        limitFlow()
        "===================".log()
        terminalOperators()
        "===================".log()
        flowOnT()
        "===================".log()
        flowWithCheck()
        "===================".log()
        flowWithCatch()
        "===================".log()


    }
}
class ConcurrentStream {

    fun consumeSequence() = productSequence().forEach {
        "$it".log()
    }

    @FlowPreview
    fun collectFlow() = runBlocking {
        launch {
            for (k in 1..3) {
                delay(100)
                "I'm not blocked $k".log()
            }
        }
        productFlow().collect {
            "1::$it".log()
        }
        withTimeoutOrNull(350) {
            productFlow().collect {
                "2::$it".log()
            }
        }
    }

    private fun productSequence() : Sequence<Int> = sequence {
        for (i in 1..3) {
            Thread.sleep(100)
            yield(i)
        }
    }

    @FlowPreview
    private fun productFlow() : Flow<Int> = flow {
        for (i in 9 downTo 5) {
            delay(300)
            emit(i)
        }
    }

    private suspend fun performRequest(request: Int) :String {
        delay(1_000)
        return "response $request"
    }

    @FlowPreview
    fun mapFlow() = runBlocking {
        (1..3).asFlow()
                .map {request ->
                    performRequest(request)
                }.collect{response -> "response :: $response".log()}
    }

    @FlowPreview
    fun transformFlow() = runBlocking {
        (1..3).asFlow()
                .transform { request -> emit("Making request $request")
                emit(performRequest(request))}
                .collect { response ->
                    response.log()
                }
    }

    @FlowPreview
    private fun numbers():Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            "This line will not execute".log()
            emit(3)
        } finally {
            "Finally in numbers".log()
        }
    }

    @FlowPreview
    fun limitFlow() = runBlocking {
        numbers().take(2).collect{value -> "limit $value".log()}
    }

    @FlowPreview
    fun terminalOperators() = runBlocking {
        "Terminal ${(1..10).asFlow().map { it * it * it }.reduce { a, b ->
            "a $a && b $b".log()
            a + b
        }}".log()
    }

    @FlowPreview
    private fun fooFlow() : Flow<Int> = flow {
        for (i in 1..3) {
            delay(1_000)
            "Emitting $i".log()
            emit(i)
        }
    }.flowOn(Dispatchers.Default)

    @FlowPreview
    private fun concatOrMerge(i: Int) : Flow<String> = flow {
        emit("$i: first")
        delay(300)
        emit("$i: second")
    }

    @FlowPreview
    fun flowOnT() = runBlocking{
        val strs = flowOf("one", "two")
        fooFlow().zip(strs){a, b -> "$a -> $b"}.collect { value -> "Collected $value".log() }
        (1..3).asFlow().onEach { delay(300) }.flatMapConcat {concatOrMerge(it)}
        .collect{ value -> "concat $value".log()}

        (5..8).asFlow().onEach { delay(300) }.flatMapMerge {concatOrMerge(it)}
                .collect{ value -> "merge $value".log()}
    }

    @FlowPreview
    fun flowWithCheck() = runBlocking {
        try {
            fooFlow().collect { value ->
                "with check $value".log()
                check(value < 2) { "Collected $value"}
            }
        } catch (e : Exception) {
            "Caught $e".log()
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun flowWithCatch() = runBlocking {
        fooFlow().onEach { check(it < 2) { "Collected $it" } }
                .onCompletion { cause -> if (null != cause) "Flow completed exceptionally".log() }
                .catch { "Caught exception".log() }
                .collect { value ->
                    check(value < 2) { "Collected $value" }
                    "$value".log()
                }
    }

}