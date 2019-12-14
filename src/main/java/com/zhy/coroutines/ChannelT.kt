package com.zhy.coroutines

import com.zhy.extensions.log
import com.zhy.extensions.productString
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.TickerMode
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun main() {
    with(ChannelT()) {
        base()
        "==================".log()
        tickerT()
    }
}

class ChannelT {
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    fun base() = runBlocking {
        val job = CoroutineScope(Dispatchers.IO).launch {
            productString().consumeEach {
                    delay(50)
                    it.log()
            }
            "Done".log()
        }

        job.join()
        job.cancelChildren()
    }

    @ObsoleteCoroutinesApi
    fun tickerT() = runBlocking {
        val tickerChannel = ticker(delayMillis = 100, initialDelayMillis = 0, mode = TickerMode.FIXED_DELAY)
        var nextElement = withTimeoutOrNull(1) {tickerChannel.poll()}
        "Initial element is available immediately: $nextElement".log()
        nextElement = withTimeoutOrNull(50) {tickerChannel.poll()}
        "Next element is not ready in 50ms: $nextElement".log()
        nextElement = withTimeoutOrNull(60) {tickerChannel.poll()}
        "Next element is ready in 100ms: $nextElement".log()

        "Consumer pauses for 150ms".log()
        delay(150)
        nextElement = withTimeoutOrNull(1) {tickerChannel.poll()}
        "Next element is available immediately after large consumer delay: $nextElement".log()

        nextElement = withTimeoutOrNull(60) {tickerChannel.poll()}
        "Next element is ready in 50ms after consumer pause in 150ms: $nextElement".log()

        tickerChannel.cancel()
    }
}