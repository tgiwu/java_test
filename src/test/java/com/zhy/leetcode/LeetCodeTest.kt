package com.zhy.leetcode

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.core.api.annotation.Inject
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith

import org.junit.Test

@RunWith(Arquillian::class)
class LeetCodeTest {

    @Inject
    private var leet: LeetCode? = null

    @Before
    fun setUp() {
        leet ?: leet.run { leet = LeetCode() }
    }

    @Test
    fun testIntReverse() {
        println("test int reverse")
        Assert.assertEquals(321, leet?.intReverse(123))
    }

    @Test
    fun testSearchInsert() {
        println("test search insert")
        Assert.assertEquals(1, leet?.searchInsert(intArrayOf(1, 2, 3, 4, 5, 10), 2))
    }

    @Test
    fun testCountAndSay() {
        println("test count and say")
        Assert.assertEquals("111221", leet?.countAndSay(5))
    }

    @Test
    fun combinationSum() {
        println("test combination sum")
        Assert.assertEquals(1,1)
//        println("${leet?.combinationSum(intArrayOf(2, 3, 6, 7), 7)}")
    }

    @Test
    fun combinationSumZ() {
        println("test combination sum z")
        val result = ArrayList<ArrayList<Int>>().apply {
            add(arrayListOf(1, 7))
            add(arrayListOf(1, 2, 5))
            add(arrayListOf(2, 6))
            add(arrayListOf(1, 1, 6))
        }

        val r = leet?.combinationSumZ(intArrayOf(10, 1, 2, 7, 6, 1, 5), 8)
    }

    @Test
    fun firstMissingPositive() {
        println("test first missing positive")
//        Assert.assertEquals(leet?.firstMissingPositive(intArrayOf(1, 2, 0)), 3)
        Assert.assertEquals(leet?.firstMissingPositive(intArrayOf(3,4,-1,1)), 2)
//        Assert.assertEquals(leet?.firstMissingPositive(intArrayOf(7,8,9,11,12)), 1)
    }

    @After
    fun tearDown() {
    }

    companion object {
        @Deployment
        fun createDeployment(): JavaArchive {
            return ShrinkWrap.create(JavaArchive::class.java, "test.jar")
                    .addClass(LeetCode::class.java)
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        }
    }
}
