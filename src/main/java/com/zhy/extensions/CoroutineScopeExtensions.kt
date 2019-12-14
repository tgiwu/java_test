package com.zhy.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

@ExperimentalCoroutinesApi
fun CoroutineScope.productString() : ReceiveChannel<String> = produce(Dispatchers.IO, 50) {
    val stream = Files.newInputStream(Paths.get("./test.csv"))
    stream.buffered().reader(Charset.forName("UTF-8")).use {
        it.readLines().forEach { line ->
            "sending $line".log()
            send(line)
        }
    }
}