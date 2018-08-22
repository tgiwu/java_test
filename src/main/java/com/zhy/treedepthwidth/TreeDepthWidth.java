package com.zhy.treedepthwidth;

import java.util.ArrayDeque;
import java.util.Queue;

public class TreeDepthWidth {
    class TreeNode{
        char val;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(char _val) {
            this.val = _val;
        }
    }

    private int getMaxDepth(TreeNode root) {
        if (root == null) return 0;
        else {
            int left = getMaxDepth(root.left);
            int right = getMaxDepth(root.right);
            return 1 + Math.max(left, right);
        }
    }

    private int getMaxWidth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        int maxWidth = 1; //最大宽度
        queue.add(root); //入队

        while(true) {
            int len = queue.size();  //当前层的节点个数
            if (len == 0) break;
            while(len > 0) { //如果当前层还有节点
                TreeNode treeNode = queue.poll();
                len--;
                if (treeNode.left != null)
                    queue.add(treeNode.left);  //下一层入队
                else
                    queue.add(treeNode.right);  //下一层入队
            }
            maxWidth = Math.max(maxWidth, queue.size());
        }
        return maxWidth;
    }
}
