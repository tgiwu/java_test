package com.zhy.leetcode

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class LeetCode constructor() {

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
        if (nums.size < 4) return result.toList()
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

    //No. 19
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {

        if (head == null) return null
        var p1: ListNode? = null
        var p2 = head
        for (i in 0 until n) {
            if (null != p1?.next) {
                p1 = p1.next
            } else {
                return null
            }
        }

        while (p1?.next != null) {
            p1 = p1.next
            p2 = p2?.next
        }

        p2?.next = p2?.next?.next

        return head
    }

    //No. 20
    fun isValid(s: String): Boolean {
        val flag = arrayOf(0, 0, 0)
        for (c in s) {
            when (c) {
                '(' -> flag[0]++
                '{' -> flag[1]++
                '[' -> flag[2]++
                ')' -> if (flag[0] == 0) return false else flag[0]--
                '}' -> if (flag[1] == 0) return false else flag[1]--
                ']' -> if (flag[2] == 0) return false else flag[2]--
            }
        }

        return flag[0] == 0 && flag[1] == 0 && flag[2] == 0
    }

    //No. 21
    /**
     * Input: 1 -> 2 -> 4, 1 -> 3 -> 4
     * Output 1-> 1-> 2 -> 3 -> 4 -> 4
     *
     * */
    fun mergeTwoSortedList(l1: ListNode?, l2: ListNode?): ListNode? {
        if (null == l1 && null == l2) return null
        l1 ?: return l2
        l2 ?: return l1
        val head = ListNode(-1)
        var p: ListNode = head
        var pl1 = l1
        var pl2 = l2
        while (null != pl1 && null != pl2) {
            if (pl1.`val` > pl2.`val`) {
                p.next = pl1
                pl1 = pl1.next
            } else {
                p.next = pl2
                pl2 = pl2.next
            }
            p = p.next!!
        }
        if (null != pl1!!.next) p.next = pl1
        if (null != pl2!!.next) p.next = pl2
        return head.next
    }

    //No. 22
    /**
     * Input: 3
     * Output: [
     * "((()))",
     * "(()())",
     * "(())()",
     * "()(())",
     * "()()()"
     * ]
     * */
    fun generateParenthesis(n: Int): List<String>? {
        val list = ArrayList<String>()
        backtrack(list, "", 0, 0, n)
        return list
    }

    private fun backtrack(list: List<String>, cur: String, start: Int, end: Int, max: Int) {
        if (cur.length == max * 2) {
            (list as ArrayList).add(cur)
            return
        }

        if (start < max) backtrack(list, "$cur(", start + 1, end, max)
        if (end < start) backtrack(list, "$cur)", start, end + 1, max)
    }

    //No. 24
    /**
     * input : 1->2->3->4
     * output: 2->1->4->3
     *
     * */
    fun swapPairs(head: ListNode?): ListNode? {
        if (null == head) return null
        val dummy = ListNode(0)
        dummy.next = head
        var cur = dummy
        while (cur.next != null && cur.next!!.next != null) {
            val p1 = cur.next
            val p2 = cur.next!!.next
            val next = p2!!.next
            p1!!.next = next
            p2.next = p1
            cur.next = p2
            cur = cur.next!!.next!!
        }
        return dummy.next
    }

    //No. 25
    /**
     * Given this linked list: 1->2->3->4->5
     *
     *For k = 2, you should return: 2->1->4->3->5
     *
     *For k = 3, you should return: 3->2->1->4->5
     * */
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        if (null == head) return null
        val dummy = ListNode(-1)
        dummy.next = head
        var cur = head
        var pre = dummy
        while (cur != null) {
            var tail = cur.next
            var i = 1
            while (tail != null && i < k) {
                tail = tail.next
                i++
            }
            if (i < k) break
            reverse(pre, cur, tail!!)
            pre = cur
            cur = tail
        }
        return dummy.next
    }

    private fun reverse(previous: ListNode, start: ListNode, tail: ListNode) {
        val endNext = tail.next
        previous.next = tail
        tail.next = start.next
        start.next = endNext
    }

    //No. 26
    /**
     * Given nums = [0,0,1,1,1,2,2,3,3,4],
     *
     *   Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.
     *
     *   It doesn't matter what values are set beyond the returned length.
     * */
    fun removeDuplicates(arr: Array<Int>): Int {
        var length = 0
        if (arr.isNotEmpty()) {
            for (index in 0 until arr.size) {
                if (index == 0) {
                    length++
                    continue
                }
                if (arr[index] == arr[index - 1]) {
                    continue
                } else {
                    length++
                }
            }
        }
        return length
    }

    //No. 27
    /**
     *Given nums = [0,1,2,2,3,0,4,2], val = 2,
     *
     *   Your function should return length = 5, with the first five elements of nums containing 0, 1, 3, 0, and 4.
     *
     *   Note that the order of those five elements can be arbitrary.
     *
     *   It doesn't matter what values are set beyond the returned length.
     * */
    fun removeElement(nums: IntArray, `val`: Int): Int {
        var i = 0
        for (j in nums.indices) {
            if (nums[j] != `val`) {
                nums[i] = nums[j]
                i++
            }
        }
        return i
    }

    //No. 28
    /**
     * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     * */
    fun strStr(s: String, needle: String): Int {
        if (s.isEmpty()) return -1
        if (needle.isEmpty()) return 0

        for (i in s.indices) {
            if (s[i] == needle[0]) {
                if (s.length - i < needle.length) {
                    return -1
                } else {
                    var k = i
                    for (j in needle.indices) {
                        if (needle[j] != s[k]) break
                        k++
                    }
                    if (k - i == needle.length) return i
                }
            }
        }
        return -1
    }

    //N0.29
    /**
     * divide tow integer without using multiplication division and mod operator
     * */
    fun divide(dividend: Int, divisor: Int): Int {
        if (dividend == 0) return 0
        if (divisor == 0 || (dividend == Int.MIN_VALUE && divisor == -1)) return Int.MAX_VALUE
        if (divisor == 1) return dividend
        if (divisor == -1) return -dividend
        val sign = dividend xor divisor < 0
        val unsignedDividend = Math.abs(dividend.toLong())
        val unsignedDivisor = Math.abs(divisor.toLong())
        if (unsignedDividend < unsignedDivisor) return 0
        val result = lDivide(unsignedDividend, unsignedDivisor)
        return if (sign) -result else result
    }

    private fun lDivide(dividend: Long, divisor: Long): Int {
        if (dividend < divisor) return 0
        var sum: Long = divisor
        var multi = 1
        while ((sum + sum) <= dividend) {
            sum += sum
            multi += multi
        }
        return multi + lDivide(dividend - sum, divisor)
    }

    //No. 30
    /**
     * Input:
     * s = "barfoothefoobarman",
     * words = ["foo","bar"]
     *  Output: [0,9]
     * Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar" respectively.
     * The output order does not matter, returning [9,0] is fine too.
     * */
    fun findSubstring(s: String, words: Array<String>): List<Int> {
        val counts = HashMap<String, Int>()
        words.forEach {
            counts[it] = counts.getOrDefault(it, 0) + 1
        }
        val indexes = ArrayList<Int>()
        val n = s.length
        val num = words.size
        val len = words[0].length

        for (i in 0 until n - num * len + 1) {
            val seen = HashMap<String, Int>()
            var j = 0
            while (j < num) {
                val word = s.substring(i + j * len, i + (j + 1) * len)
                if (counts.containsKey(word)) {
                    seen[word] = seen.getOrDefault(word, 0) + 1
                    if (seen[word]!! > counts.getOrDefault(word, 0)) {
                        break
                    }
                } else {
                    break
                }
                j++
            }
            if (j == num) {
                indexes.add(i)
            }
        }
        return indexes
    }

    //No. 31
    /**
     *  I am not sure what the description want - -||
     * */
    fun nextPermutation(nums: Array<Int>) {
        var i = nums.size - 2
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--
        }
        if (i >= 0) {
            var j = nums.size - 1
            while (j >= 0 && nums[j] <= nums[i]) {
                j--
            }
            swap(nums, i, j)
        }
        reverse(nums, i + 1)
    }

    private fun reverse(nums: Array<Int>, start: Int) {
        var i = start
        var j = nums.size - 1
        while (i < j) {
            swap(nums, i, j)
            i++
            j--
        }
    }

    private fun swap(nums: Array<Int>, i: Int, j: Int) {
        val temp = nums[i]
        nums[i] = nums[j]
        nums[j] = temp
    }

    //No. 32
    /**
     * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
     * */
    fun longestValidParentheses(s: String): Int {
        var valid = 0
        var start = 0
        var result = 0
        while (start < s.length) {
            for (i in start until s.length) {
                when (s[i]) {
                    '(' -> valid++
                    ')' -> valid--
                }
                if (valid == 0) {
                    result = Math.max(result, i - start + 1)
                    println("result $result")
                } else if (valid < 0) {
                    valid = 0
                    break
                }
                if (i == s.length - 1) valid = 0

            }
            if (result > s.length - start) break
            start++
        }
        return result
    }

    //No. 32 using dynamic programming
    fun longestValidParenthesesDP(s: String): Int {
        var maxans = 0
        val dp = Array(s.length) { 0 }
        for (i in 1 until s.length) {
            if (s[i] == ')') {
                if (s[i - 1] == '(') {
                    dp[i] = 2 + if (i >= 2) dp[i - 2] else 0
                } else if (i - dp[i - 1] > 0 && s[i - dp[i - 1] - 1] == '(') {
                    dp[i] = dp[i - 1] + 2 + (if (i - dp[i - 1] >= 2) dp[i - dp[i - 1] - 2] else 0)
                }
                maxans = Math.max(maxans, dp[i])
            }
        }
        for (i in dp) {
            print("$i, ")
        }
        return maxans
    }

    //No. 32 using stack
    fun longestValidParenthesesStack(s: String): Int {
        var max = 0
        val stack = Stack<Int>()
        stack.push(-1)
        for (i in 0 until s.length) {
            if (s[i] == '(') {
                stack.push(i)
            } else {
                stack.pop()
                if (stack.empty()) {
                    stack.push(i)
                } else {
                    max = Math.max(max, i - stack.peek())
                }
            }
        }
        return max
    }

    //No.33
    /**
     * Search in Rotated Sorted Array
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     *
     * make sure if target in sorted or not
     * */
    fun search(nums: IntArray, target: Int): Int {
        var low = 0
        var high = nums.size - 1
        while (low <= high) {
            val mid = low + (high - low) / 2
            if (mid == target) return mid
            if (nums[low] < nums[mid]) {
                if (target >= nums[low] && target < nums[mid]) {
                    high = mid - 1
                } else {
                    low = mid + 1
                }
            } else {
                if (target > nums[mid] && target <= nums[high]) {
                    low = mid + 1
                } else {
                    high = mid - 1
                }
            }
        }
        return -1
    }

    //No. 34
    /**
     * find first and last posotion of element in sorted array
     *
     * */
    fun searchRange(nums: IntArray, target: Int): IntArray {
        if (nums.isEmpty() || (nums[0] > target && nums[nums.lastIndex] < target)) return intArrayOf(-1, -1)
        var low = 0
        var high = nums.lastIndex
        var index = -1
        while (low <= high) {
            val mid = low + (high - low) / 2
            if (nums[mid] == target) {
                index = mid
                break
            }
            if (target < nums[mid]) {
                high = mid - 1
            } else {
                low = mid + 1
            }
        }
        println("index $index")
        if (-1 == index) return intArrayOf(-1, -1)
        low = index
        high = index
        var lIt = true
        var rIt = true
        while (low > 0 || high < nums.lastIndex) {
            if (low == 0) lIt = false
            else if (nums[--low] != target) {
                low++
                lIt = false
            }
            if (high == nums.lastIndex) rIt = false
            else if (nums[++high] != target) {
                high--
                rIt = false
            }

            if (!lIt && !rIt) break
        }
        return intArrayOf(low, high)
    }

    //No.35
    /**
     * search insert position
     *
     * */
    fun searchInsert(nums: IntArray, target: Int): Int {
        if (target <= nums[0]) return 0
        if (target > nums[nums.lastIndex]) return nums.size
        if (target == nums[nums.lastIndex]) return nums.lastIndex
        var low = 0
        var high = nums.lastIndex
        var mid = 0
        while (low <= high) {
            mid = low + (high - low) / 2
            if (nums[mid] == target) return mid
            if (nums[mid] < target) {
                low = mid + 1
            } else {
                high = mid - 1
            }
        }
        return if (nums[mid] < target) {
            mid + 1
        } else {
            mid
        }
    }

    //No.36
    /**
     * Valid Sudoku
     * */
    fun isValidSudoku(board: Array<CharArray>): Boolean {

        for (i in 0..8) {
            val rowSet = HashSet<Char>()
            val colSet = HashSet<Char>()
            val cubSet = HashSet<Char>()
            for (j in 0..8) {
                if (board[i][j] != '.' && !rowSet.add(board[i][j])) return false
                if (board[i][j] != '.' && !colSet.add(board[i][j])) return false
                val r = i / 3 *3 + j / 3
                val c = i % 3 * 3 + j % 3
                if (board[r][c] != '.' && !cubSet.add(board[r][c])) return false
            }
        }
        return true
    }

    //No. 37
    /**
     * solve sudoku
     * */
    fun solveSudoku(board: Array<CharArray>) {
        if (board.isEmpty()) return
        solve(board)
    }
    private fun solve(board:Array<CharArray>) : Boolean {
        for (i in 0 until board.size) {
            for (j in 0 until board[i].size) {
                if (board[i][j] == '.') {
                    for (c in '0'..'9') {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c
                            if (solve(board))
                                return true
                            else
                                board[i][j] = '.'
                        }
                    }
                    return false
                }
            }
        }
        return true
    }
    private fun isValid(board: Array<CharArray>, row:Int, col:Int, char:Char) :Boolean{
        for (i in 0..8) {
            if (board[i][col] != '.' && board[i][col] == char) return false
            if (board[row][i] != '.' && board[row][i] == char) return false
            val r = 3 * (row / 3) + i / 3
            val c = 3 * (col / 3) + i % 3
            if (board[r][c] != '.' && board[r][c] == char) return false
        }
        return true
    }

    //No. 38
    /**
     * count and say
     * */
    fun countAndSay(n: Int): String {
        return solve("1", n - 1)
    }
    private fun solve(s: String, n: Int): String {
        if (n == 0) return s
        var i = 0
        val builder = StringBuilder()
        while (i < s.length) {
            var count = 1
            while (i + 1 < s.length && s[i] == s[i + 1]) {
                i++
                count++
            }
            builder.append(count)
            builder.append(s[i])
            i++
        }
        return solve(builder.toString(), n - 1)
    }

    //No.39
    /**
     * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target),
     * find all unique combinations in candidates where the candidate numbers sums to target.
     *
     * */
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        val ans = ArrayList<ArrayList<Int>>()
        Arrays.sort(candidates)
        combinationSumSolve(ans, ArrayList(), candidates, 0, target)
        return ans
    }
    private fun combinationSumSolve(result: ArrayList<ArrayList<Int>>, list: ArrayList<Int>, cand: IntArray, from: Int, remain: Int) {
        if (0 > remain) return
        if (0 == remain) {
            result.add(ArrayList(list))
            return
        }
        for (i in from until cand.size) {
            list.add(cand[i])
            combinationSumSolve(result, list, cand, i, remain - cand[i])
            list.removeAt(list.size - 1)
        }
    }

    //No.40
    /**
     *Given a collection of candidate numbers (candidates) and a target number (target),
     * find all unique combinations in candidates where the candidate numbers sums to target.
     * */
    fun combinationSumZ(candidates:IntArray, target: Int) : List<List<Int>> {
        val result = ArrayList<ArrayList<Int>>()
        Arrays.sort(candidates)
        combinationSumZSolve(result, ArrayList(), candidates, 0, target)
        return result
    }
    private fun combinationSumZSolve(result: ArrayList<ArrayList<Int>>, list:ArrayList<Int>, cand:IntArray, from: Int, remain: Int) {
        if (remain < 0) return
        if (remain == 0) {
            list.sort()
            if (!result.contains(list)) result.add(ArrayList(list))
            return
        }
        for (i in from until cand.size) {
            if (cand[i] > remain) break
            if(i > from && cand[i] == cand[i - 1]) continue

            list.add(cand[i])
            combinationSumZSolve(result, list, cand, from + 1, remain - cand[i])
            list.removeAt(list.size - 1)
        }
    }

    //No.41
    /**
     * Given an unsorted integer array, find the smallest missing positive integer.
     * */
    fun firstMissingPositive(nums: IntArray) : Int {
        for (i in nums.indices) {
            if (nums[i] <= 0 || nums[i] > nums.size) {
                nums[i] = nums.size + 1
            }
        }
        for ( i in nums) {
            print(" $i ")
            var n = Math.abs(i)
            if (n > nums.size) continue
            n--
            if (nums[n] > 0 )
                nums[n] = -nums[n]
        }
        for (i in 0 until nums.size) {
            if (nums[i] >= 0) {
                return i + 1
            }
        }

        return nums.size + 1
    }

}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

