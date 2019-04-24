package com.zhy.leetcode

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet


fun main(args: Array<String>) {
    val leet = LeetCode()
//    leet.twoSum(intArrayOf(2, 7, 11, 15),9)
//    println("lengthOfLongestSubString ${leet.lengthOfLongestSubstring("abcabcdd")}")
//    println("longestPalindrome ${leet.longestPalindrome("dgdgdgadagwegsdg")}")
//    println("zigzag ${leet.convert("PAYPALISHIRING", 3)}")
//    println("int reverse ${leet.intReverse(-23523463)}")
//    println("myatoi ${leet.myAtoi("124sdhfhgdfgh")}")
//    println("max area ${leet.maxArea(intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7))}")
//    println("int to roman ${leet.intToRoman(199999)}")
//    println("max common subString ${leet.maxCommonSubstring(arrayOf("leet", "leets", "eleetcode"))}")
//    println("max common subString ${leet.maxCommonSubStringByTrie(arrayOf("leet", "leets", "leetcode"))}")
//    println("three sum is zero ${leet.threeSum(intArrayOf(-1, 0, 1, 2, -1, -4))}")
//    println("three sum closest ${leet.threeSumClosest(intArrayOf(1, 2, 4, 8, 16, 32, 64, 128), 82)}")
//    println("letter combination ${leet.letterCombinations("2")}")
    println("four sum ${leet.fourSum(intArrayOf(1, 0, -1, 0, 2, 3, -5, 4), 0)}")
}

class LeetCode {

    //No. 1
    fun twoSum(arr: IntArray, target: Int): IntArray? {
        val map: HashMap<Int, Int> = HashMap()
        for (i in arr.indices) {
            val need = target - arr[i]
            println("need is $need")
            if (map[need] != null) {
                print("result ${map[need]}, $i")
                return intArrayOf(map[need]!!, i)
            } else {
                println("put $target = $i")
                map[arr[i]] = i
            }
        }
        return null
    }

    //No. 3
    fun lengthOfLongestSubstring(s: String): Int {
        val n = s.length
        val checkSet = HashSet<Char>()
        var ans = 0
        var i = 0
        var j = 0
        while (i < n && j < n) {
            if (!checkSet.contains(s[j])) {
                checkSet.add(s[j])
                println("add ${s[j]}")
                j++
                ans = Math.max(ans, j - i)
            } else {
                checkSet.remove(s[i])
                println("remove ${s[i]}")
                i++
            }

        }
        return ans
    }

    //No. 5
    fun longestPalindrome(s: String): String {
        if (s.isEmpty()) return ""
        if (s.length == 1) return s
        var start = 0
        var end = 0
        for (i in 0 until s.length) {
            val len1 = expandAroundCenter(s, i, i)
            val len2 = expandAroundCenter(s, i, i + 1)
            val len = Math.max(len1, len2)
            if (len > end - start) {
                start = i - (len - 1) / 2
                end = i + len / 2
            }
        }
        return s.substring(start, end + 1)
    }

    private fun expandAroundCenter(s: String, l: Int, r: Int): Int {
        var left = l
        var right = r
        while (left > 0 && right < s.length && s[left] == s[right]) {
            left--
            right++
        }
        return right - left - 1
    }

    //No. 6
    fun convert(s: String, numRows: Int): String {
        val builderList = ArrayList<StringBuilder>()
        for (i in 0 until numRows) {
            builderList.add(StringBuilder())
        }
        var index = 0
        var indexDown = false
        s.forEach {
            builderList[index].append(it)
            if (index == 0 || index == numRows - 1) indexDown = !indexDown
            if (indexDown) index++ else index--
            println("index $index")
        }

        val result = StringBuilder()
        builderList.forEach {
            println("it $it")
            result.append(it)
        }
        return result.toString()
    }

    //No. 7
    fun intReverse(x: Int): Int {
        var localX = x
        var result = 0
        while (localX != 0) {
            val pop = localX % 10
            localX /= 10
            result = result * 10 + pop
            println("current int $result  x  $localX")
        }
        return result
    }

    //No. 8
    fun myAtoi(s: String): Int {
        s.also { it.trim() }

        var sign = 1
        var start = 0
        if (s[0] == '+') {
            sign = 1
            start++
        } else if (s[0] == '-') {
            sign = -1
            start++
        }
        var sum: Long = 0
        s.substring(start).forEach {
            if (!it.isDigit()) {
                sum = (sum * sign)
                return sum.toInt()
            }

            sum = sum * 10 + Integer.parseInt(it.toString())

            if (sign == 1 && sum > Int.MAX_VALUE) {
                sum = Int.MAX_VALUE.toLong()
                return sum.toInt()
            }
            if (sign == -1 && sum < Int.MIN_VALUE) {
                sum = Int.MIN_VALUE.toLong()
                return sum.toInt()
            }
        }

        return (sum * sign).toInt()
    }

    //No.11
    fun maxArea(height: IntArray): Int {
        var l = 0
        var r = height.size - 1
        var area = 0
        while (r > l) {
            area = Math.max(area, Math.min(height[l], height[r]) * (r - l))
            if (height[l] > height[r]) {
                r--
            } else {
                l++
            }
        }
        return area
    }

    //No. 13
    fun intToRoman(num: Int): String {
        var localNum = num
        val d = TreeMap<Int, String>()
        d[1000] = "M"
        d[900] = "CM"
        d[500] = "D"
        d[400] = "CD"
        d[100] = "C"
        d[90] = "XC"
        d[50] = "L"
        d[40] = "XL"
        d[10] = "X"
        d[9] = "IX"
        d[5] = "V"
        d[4] = "IV"
        d[1] = "I"
        val result = StringBuilder()
        for (e in d.descendingKeySet()) {
            val n = localNum / e
            localNum -= n * e
            for (i in 1..n) {
                result.append(d[e])
            }
        }
        return result.toString()
    }

    fun maxCommonSubstring(array: Array<String>): String {
        if (array.isEmpty() || array[0].isEmpty()) return ""
        var result = array[0]
        for (i in 1 until array.size) {
            while (array[i].indexOf(result) != 0) {
                println("before sub $result")
                result = result.run { substring(0, result.length - 1) }
                println("after sub $result")
                if (result.isEmpty()) return ""
            }
        }
        return result
    }

    fun maxCommonSubStringByTrie(array: Array<String>): String {
        if (array.isEmpty() || array[0].isEmpty()) return ""
        if (array.size == 1) return array[0]

        val trie = Trie()

        for (i in 1 until array.size) {
            trie.insert(array[i])
        }
        return trie.searchLongestPrefix(array[0])
    }

    //No. 15
    fun threeSum(nums: IntArray): List<List<Int>>? {
        val sorted = nums.sortedArray()
        val result = ArrayList<List<Int>>()
        for (i in sorted.indices) {
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue
            }
            var j = i + 1
            var k = sorted.size - 1
            while (j < k) {
                val sum = sorted[i] + sorted[j] + sorted[k]
                if (sum == 0) {
                    result.add(arrayListOf(sorted[i], sorted[j], sorted[k]))
                    k--
                    j++
                    while (j < k && sorted[j] == sorted[j - 1]) j++
                    while (j < k && k + 1 < sorted.size && sorted[k] == sorted[k + 1]) k--
                }
                if (sum > 0) k--
                if (sum < 0) j++

            }
        }
        return result.toList()
    }

    //No. 16
    fun threeSumClosest(nums: IntArray, target: Int): Int {
        val array = nums.sortedArray()
        var result = 0
        var resultGap = Int.MAX_VALUE
        for (i in array.indices) {
            var j = i + 1
            var k = array.size - 1
            while (j < k) {
                val sum = array[i] + array[j] + array[k]
                val gap = Math.abs(sum - target)
                if (gap < resultGap) {
                    result = sum
                    resultGap = gap
                }

                when {
                    sum > target -> do {
                        k--
                    } while (j < k && array[k] == array[k + 1])
                    sum < target -> do {
                        j++
                    } while (j < k && array[j] == array[j - 1])
                    else -> return result
                }
            }
        }
        return result
    }

    //No. 17
    fun letterCombinations(digits: String): List<String> {
        if (digits.isEmpty()) return ArrayList()
        val digitsChar = arrayOf(charArrayOf('a', 'b', 'c'), charArrayOf('d', 'e', 'f'), charArrayOf('g', 'h', 'i'),
                charArrayOf('j', 'k', 'f'), charArrayOf('m', 'n', 'o'), charArrayOf('p', 'q', 'r', 's'),
                charArrayOf('t', 'u', 'v'), charArrayOf('w', 'x', 'y', 'z'))
        val list1 = ArrayList<String>()
        val list2 = ArrayList<String>()
        var isUseList1 = false
        for (c in digits.indices) {
            if (digits[c].isDigit() && '2' <= digits[c] && '9' >= digits[c]) {
                isUseList1 = !isUseList1
                if (isUseList1) {
                    list1.clear()
                    if (0 == list2.size) {
                        digitsChar[digits[c] - '2'].forEach {
                            list1.add(it.toString())
                        }
                    } else {
                        digitsChar[digits[c] - '2'].forEach {
                            list2.forEach { s ->
                                list1.add(s + it.toString())
                            }
                        }
                    }
                } else {
                    list2.clear()
                    digitsChar[digits[c] - '2'].forEach {
                        list1.forEach { s ->
                            list2.add(s + it.toString())
                        }
                    }
                }
            }
        }
        return if (isUseList1) list1 else list2
    }

    //No. 18
    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {

        val result = HashSet<ArrayList<Int>>()
        if (nums.size < 4 ) return result.toList()
        if (nums.size == 4) {
            if (nums[0] + nums[1] + nums[2] + nums[3] == target) {
                result.add(nums.toList() as ArrayList<Int>)
            }
            return result.toList()
        }

        val arr = nums.sorted()
        val map = HashMap<Int, ArrayList<IntArray>>()

        for (i in 0 until arr.size) {
            for (j in i + 1 until arr.size) {
                val sum = arr[i] + arr[j]
                if (map.containsKey(target - sum)) {
                    val indexList = map[target - sum]!!
                    for (index in indexList) {
                        if (index[1] < i) {
                            result.add(arrayListOf(arr[index[0]], arr[index[1]], arr[i], arr[j]))
                        }
                    }
                }
                val temp = map.getOrDefault(sum, ArrayList())
                temp.add(intArrayOf(i, j))
                map[sum] = temp
            }
        }
        return result.toList()
    }
}