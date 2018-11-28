package com.zhy.kt

class AvlTreeInterger() {
    val QIANXU = 1
    val ZHONGXU = 2
    val HOUXU = 3
    val CENGJI = 4

    var root : AvlNodeInteger? = null
    var size : Int = 0

    constructor(root : AvlNodeInteger?) : this() {
        this.root = root
    }

    fun initRoot(value : Int) {
        this.root = AvlNodeInteger(value)
        print("root value")
    }

    fun insert(value :Int) {
        root?: apply {
            initRoot(value)
            size++;
            return
        }
        if (contains(value)) throw Exception("already exist")
        insertNode(root, value)
        size++
    }

    private fun createSingleNode(value: Int) : AvlNodeInteger = AvlNodeInteger(value)

    fun contains(value: Int) : Boolean {
        var curNode : AvlNodeInteger? = root
        curNode?: return false
        while (null != curNode) {
            curNode = when {
                value > curNode.value -> curNode.right
                value < curNode.value -> curNode.left
                else -> return true
            }
        }
        return false
    }

    fun remove(value : Int) {
        if ((null == value) or (null == root)) {
            return
        }
        if (!contains(value)) {
            return
        }
        remove(root, value)
    }

    private fun remove(parent: AvlNodeInteger?, value: Int) : AvlNodeInteger? {
        var tempParent = parent
        if (value < parent!!.value) {
            var newLeft = remove(tempParent!!.left, value)
            tempParent.left = newLeft
            if (height(tempParent.right) - height(tempParent.left) > 1) {
                var tempNode = tempParent.right
                if (height(tempNode!!.left) > height(tempNode.right)) {
                    rightLeftRotate(tempParent)
                } else {
                    rightRightRotate(tempParent)
                }
            }
        } else if (value > tempParent!!.value) {
            val newRight = remove(tempParent.right, value)
            tempParent.right = newRight
            if (height(tempParent.left) - height(tempParent.right) > 1) {
                var tempNode = tempParent.left
                if (height(tempNode!!.left) > height(tempNode.right)) {
                    leftLeftRotate(tempParent)
                } else {
                    leftRightRotate(tempParent)
                }
            }
        } else {
            if ((null != tempParent.left) and (null != tempParent.right)) {
                if (tempParent.left!!.height!! > tempParent.right!!.height!!) {
                    var leftMax = getMax(tempParent.left)
                    tempParent.left = remove(tempParent.left, leftMax!!.value)
                    leftMax.left = tempParent.left
                    leftMax.right = tempParent.right
                    leftMax.height = maxHeight(leftMax.left!!, leftMax.right!!)
                    tempParent = leftMax
                } else {
                    var rightMin = getMin(tempParent.right)
                    tempParent.right = remove(tempParent.right, rightMin!!.value)
                    rightMin.left = tempParent.left
                    rightMin.right = tempParent.right
                    rightMin.height = maxHeight(tempParent.left!!, tempParent.right!!) + 1
                    tempParent= rightMin
                }
            } else {
                tempParent = null
            }
        }
        return tempParent
    }

    private fun insertNode(parent: AvlNodeInteger?, value: Int) : AvlNodeInteger{
        var tempParent : AvlNodeInteger? = parent
        parent?: createSingleNode(value)
        if (value < parent!!.value) {
            parent.left = insertNode(parent.left, value)

            if (height(parent.left) - height(parent.right) > 1) {
                var compareVal = parent.left!!.value

                tempParent = if (value < compareVal) {
                    leftLeftRotate(parent)
                } else {
                    rightLeftRotate(parent)
                }
            }
        }

        if (value > parent.value) {
            tempParent!!.right = insertNode(parent.right, value)
            if (height(tempParent.right) - height(tempParent.left) > 2) {
                var compareVal = tempParent.left!!.value
                tempParent = if (value > compareVal)
                    rightRightRotate(tempParent)
                else
                    rightLeftRotate(tempParent)
            }
        }
        tempParent!!.height = maxHeight(tempParent.left!!, tempParent.right!!) + 1
        return tempParent
    }

    private fun leftLeftRotate(node: AvlNodeInteger) : AvlNodeInteger{
        var newNode = node.left
        node.left = newNode?.right
        newNode?.right = node

        node.height = maxHeight(newNode?.left!!, newNode.right!!) + 1
        newNode.height = maxHeight(newNode.left!!, newNode.right!!) + 1
        return newNode
    }

    private fun rightRightRotate(node: AvlNodeInteger) : AvlNodeInteger {
        var newNode = node.right
        node.left = newNode!!.left
        newNode.left = node

        node.height = maxHeight(node.left!!, node.right!!)
        newNode.height = maxHeight(newNode.left!!, newNode.right!!)
        return newNode
    }

    private fun leftRightRotate(node : AvlNodeInteger) :AvlNodeInteger{
        node.left = rightRightRotate(node.left!!)
        return leftLeftRotate(node)
    }

    private fun rightLeftRotate(node: AvlNodeInteger) : AvlNodeInteger {
        node.right = leftLeftRotate(node.right!!)
        return rightRightRotate(node)
    }

    private fun getMax(currentRoot: AvlNodeInteger?) : AvlNodeInteger?{
        var current : AvlNodeInteger? = null
        currentRoot?.right?.let {
            current = getMax(it.right)
        }
        return current
    }

    private fun getMin(currentRoot : AvlNodeInteger?) : AvlNodeInteger? {
        var current : AvlNodeInteger? = null
        currentRoot?.left?.let {
            current = getMin(it.left)
        }
        return current
    }

    fun findMax() : AvlNodeInteger? {
        root?: return null
        var temp = root
        while (null != temp?.right) {
            temp = temp.right
        }
        return temp
    }

    fun findMin() : AvlNodeInteger? {
        root?: return null
        var temp = root
        while (null != temp?.left) {
            temp = temp.left
        }
        return temp
    }

    private fun maxHeight(left : AvlNodeInteger, right: AvlNodeInteger) : Int{
        return if(height(left) > height(right)) height(left) else height( right)
    }

    private fun height(t : AvlNodeInteger?) : Int {
        return if (null == t) 0 else t.height!!
    }

    fun getNodeSize() :Int = size

    fun printGraph(style: Int?) {
        root ?: return
        when(style) {
            QIANXU -> xianxu(root)
            ZHONGXU -> zhongxu(root)
            HOUXU -> houxu(root)
            CENGJI -> {
                val a = ArrayList<AvlNodeInteger?>()
                a.add(root)
                cengji(a)
            }
        }
    }

    private fun xianxu(parent : AvlNodeInteger?) {
        println(parent!!.value)
        parent.left?.let {
            xianxu(it)
        }
        parent.right?.let {
            xianxu(it)
        }
    }

    private fun zhongxu(parent: AvlNodeInteger?) {
        parent!!.left?.let {
            zhongxu(it)
        }
        println(parent.value)
        parent.right?.let {
            zhongxu(parent)
        }
    }

    private fun houxu(parent: AvlNodeInteger?) {
        parent!!.left?.let {
            houxu(it)
        }
        parent.right?.let {
            houxu(it)
        }
        println(parent.value)
    }

    private fun cengji(parents : List<AvlNodeInteger?>?) {
        if ((null == parents) or (parents?.isEmpty()!!)) return

        var avlNodeIntegers = ArrayList<AvlNodeInteger>()
        var k = 0;
        for (i in 0..parents.size) {
            var currentNode = parents.get(i)
            println("${currentNode!!.value} ,")
            currentNode.left?.let {
                avlNodeIntegers.add(it)
                k++
            }
            currentNode.right?.let {
                avlNodeIntegers.add(it)
                k++
            }
        }
        println("-----------------------------------------------------")
        cengji(avlNodeIntegers)
    }


    class AvlNodeInteger(t : Int, left: AvlNodeInteger?, right: AvlNodeInteger?, height: Int?) {

        var value = t
        var left = left
        var right = right
        var height = height

        constructor(t: Int) : this(t, null, null, 1)

        constructor(t: Int, left: AvlNodeInteger?, right:AvlNodeInteger) : this(t, left, right, null)

    }
}