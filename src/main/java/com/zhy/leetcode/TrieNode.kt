package com.zhy.leetcode

class TrieNode {

    private lateinit var links: Array<TrieNode?>

    private val R = 26

    private var isEnd = false

    private var size = 0

    init {
        links = Array(R) { null }
    }

    fun containsKey(ch: Char): Boolean = links[ch - 'a'] != null

    fun getSize() : Int = size

    fun get(ch: Char): TrieNode? = links[ch - 'a']

    fun put(ch: Char, node: TrieNode) {
        links[ch - 'a'] = node
        size++
    }

    fun setEnd() {
        isEnd = true
    }

    fun isEnd(): Boolean = isEnd
}