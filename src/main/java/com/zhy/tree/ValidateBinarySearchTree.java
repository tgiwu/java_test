package com.zhy.tree;

import java.util.Stack;

public class ValidateBinarySearchTree {
    public static void main(String[] args) {
        System.out.println("args");
    }

    private boolean isValidBST(Node node) {
        if (null == node) return true;
        Stack<Node> stack = new Stack<>();
        Node pre = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            if (pre != null && pre.val >= node.val) return false;
            pre = node;
            node = node.right;
        }
        return true;
    }

    public boolean isValidBST(Node node, long minVal, long maxVal) {
        if (node == null) return true;
        if (node.val >= maxVal || node.val <= minVal) return false;
        return isValidBST(node.left, minVal, node.val) && isValidBST(node.right, node.val, maxVal);
    }

    class Node {
        long val;
        Node left, right;
    }
}
