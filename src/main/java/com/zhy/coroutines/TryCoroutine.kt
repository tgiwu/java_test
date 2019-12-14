package com.zhy.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TryCoroutine : CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private suspend fun createAJob(i : Int): Int {
        println("launch a job $i")
        delay(i * 1000L)
        println("job $i finished")
        return i * i
    }

    suspend fun launchJob() {
        val job1 = launch {
                createAJob(1)
            }


        val job2 = launch {
                createAJob(2)
            }

        job1.join()
        job2.join()
    }
}