package com.zhy.extensions

fun String.log() {
    println("[${Thread.currentThread().name}] $this")
}