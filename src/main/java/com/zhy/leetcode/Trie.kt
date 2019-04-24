package com.zhy.leetcode

class Trie {
    private var root: TrieNode = TrieNode()

    fun insert(word: String) {
        var node = root
        for (c in word) {
            if (!node.containsKey(c)) {
                node.put(c, TrieNode())
            }
            node = node.get(c)!!
        }
        node.setEnd()
    }

    private fun searchPrefix(word: String): TrieNode? {
        var node = root
        for (c in word) {
            if (node.containsKey(c)) {
                node = node.get(c)!!
            } else {
                return null
            }
        }
        return node
    }

    fun search(word: String): Boolean = searchPrefix(word).run {
        this != null && this.isEnd()
    }

    fun startWith(prefix: String): Boolean = searchPrefix(prefix) != null

    fun searchLongestPrefix(word: String) :String {
        var node = root

        val prefix = StringBuilder()

        for (c in word) {
            if (node.containsKey(c) && node.getSize() == 1 && !node.isEnd()) {
                prefix.append(c)
            } else {
                return prefix.toString()
            }
        }
        return prefix.toString()
    }
}