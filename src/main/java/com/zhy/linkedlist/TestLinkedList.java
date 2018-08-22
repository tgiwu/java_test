package com.zhy.linkedlist;

public class TestLinkedList {
    public class Node {
        int index;
        Node next;
        int data;
        public Node(int index, Node next, int data) {
            this.index = index;
            this.next = next;
            this.data = data;
        }
    }

    //迭代法，现将下一节点记录下来，然后让当前节点指向上一节点，
    // 再将当前节点记录下来，再让下一节点变为当前节点
    public Node reverse(Node node) {
        Node prev = null;
        Node now = node;
        while (now != null) {
            Node next = now.next;
            now.next = prev;
            prev = now;
            now = next;
        }
        return  prev;
    }

    //递归法， 先找到最后一个节点，然后从最后一个节点开始反转 ，
    // 然后当前节点反转时其后面的节点已经进行反转了，不需要管。最后返回最后一个节点
    public Node reverse2(Node node, Node prev) {
        if (node.next == null) {
            node.next = prev;
            return node;
        } else {
            Node re = reverse2(node.next, node);
            node.next = prev;
            return re;
        }
    }

    //查找链表中点
    private Node findMidNode(Node head) {
        if (null == head) return null;

        Node first = head;
        Node second = head;

        while(second != null && second.next != null) {
            first = first.next;
            second = second.next.next;
        }

        return first;
    }

    //链表合并
    //链表一： 1 -> 2 -> 3 -> 4
    //链表二：2 -> 3 -> 4 -> 5
    //合并后：1 -> 2 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5
    private Node mergeLinkList(Node head1, Node head2){
        if (head1 == null && head2 == null) {
            return null;
        }
        if (head1 == null) return head2;
        if (head2 == null) return head1;

        Node head, current;

        if (head1.data < head2.data) {
            head = head1;
            current = head1;
            head1 = head1.next;
        } else {
            head = head2;
            current = head2;
            head2 = head2.next;
        }

        while (head1 != null && head2 != null) {
            if (head1.data < head2.data) {
                current.next = head1;
                current = current.next;
                head1 = head1.next;
            } else {
                current.next = head2;
                current = current.next;
                head2 = head2.next;
            }
        }

        if (head1 != null) {
            current.next = head1;
        }

        if (head2 != null) {
            current.next = head2;
        }

        return  head;
    }

    //判断链表是否有环
    private boolean hasCycle(Node head) {
        if (head == null) {
            return false;
        }

        Node first = head;
        Node second = head;

        while (second != null) {
            first = first.next;
            second = second.next.next;
            if ( first == second) {
                return true;
            }
        }
        return false;
    }

    //获取环长度
    private int getCycleLength(Node head) {
        if (head == null) return 0;
        Node current = head;
        int length = 0;
        while (current != null) {
            current = current.next;
            length++;
            if (current == head) {
                return length;
            }
        }
        return length;
    }

    //判断连个单链表相交的第一个交点
    private Node getFirstCommonNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) return null;

        int length1 = getLength(head1);
        int length2 = getLength(head2);
        int lengthDif = 0;

        Node longHead, shortHead;

        if (length1 > length2) {
            longHead = head1;
            shortHead = head2;
            lengthDif = length1 - length2;
        } else {
            longHead = head2;
            shortHead = head1;
            lengthDif = length2 - length1;
        }

        for (int i = 0; i < lengthDif; i++) {
            longHead = longHead.next;
        }

        while (longHead != null && shortHead != null){
            if (longHead == shortHead) return longHead;
            longHead = longHead.next;
            shortHead = shortHead.next;
        }
        return null;
    }

    //获取链表长度
    private int getLength(Node head){
        if (head == null) {
            return 0;
        }
        int length = 0;
        Node current = head;
        while(current != null) {
            length++;
            current = current.next;
        }
        return length;
    }
}
