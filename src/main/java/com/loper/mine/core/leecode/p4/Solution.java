package com.loper.mine.core.leecode.p4;

import java.util.Stack;

/**
 * @author liaonanzhou
 * @date 2021/12/1 18:22
 * @description https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/
 **/
public class Solution {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;

        // 单链测试
        ListNode reverse = reverseList2(node1);
        while (reverse != null) {
            System.out.println(reverse.val);
            reverse = reverse.next;
        }

        System.out.println("=========================");

        // 回文链表测试
        node6.next = node1;
        ListNode reverseHuiwen = reverseListHuiwen1(node1);
        ListNode start = reverseHuiwen;
        while (reverseHuiwen != null) {
            System.out.println(reverseHuiwen.val);
            reverseHuiwen = reverseHuiwen.next;
            if (reverseHuiwen == start)
                break;
        }
    }

    /*
     * 方式：stack
     *
     * 缺点：回文链表会导致死循环
     */
    public static ListNode reverseList(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        while (head != null) {
            stack.add(head);
            head = head.next;
        }

        ListNode newHead = null;
        ListNode tail = null;
        while (!stack.empty()) {
            ListNode node = new ListNode(stack.pop().val);
            if (newHead == null) {
                newHead = node;
                tail = newHead;
            } else {
                tail.next = node;
                tail = tail.next;
            }
        }

        return newHead;
    }

    /*
     * 方式：递归
     *
     *      原head节点 -> tail 节点
     *      head 节点持续下移并 new Node
     *      原tail节点 -> head 节点
     *
     * 缺点：回文链表会导致死循环
     */
    public static ListNode reverseList1(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode oldHead = newHead;
            newHead = new ListNode(head.val);
            newHead.next = oldHead;
            head = head.next;
        }

        return newHead;
    }

    /*
     * 递归+回溯
     */
    public static ListNode reverseList2(ListNode head) {
        return reverse(head, null);
    }

    /* 入栈(递归)    出栈（回溯）
     * | 5       null ↑
     * | 4          5 |
     * | 3          4 |
     * | 2          3 |
     * | 1          2 |
     * ↓ null       1 |
     *
     * 参数：
     * out.next = in
     *
     * 缺点：回文链表会导致栈溢出：StackOverflowError
     */
    public static ListNode reverse(ListNode in, ListNode out) {
        if (in == null)
            return out;
        ListNode node = reverse(in.next, in);
        in.next = out;
        return node;
    }

    //=============================================[逆转回文链表]

    /*
     * 原：
     * 1 -> 2 -> 3 -> 5
     * ↑______________|
     * 反转：
     * 1 <- 2 <- 3 <- 5
     * |______________↑
     */
    public static ListNode reverseListHuiwen1(ListNode head) {
        ListNode startHead = head;
        ListNode newHead = null;
        while (head != null) {
            ListNode oldHead = newHead;
            newHead = new ListNode(head.val);
            newHead.next = oldHead;
            head = head.next;
            if (head == startHead)
                break;
        }

        return newHead;
    }


    public static ListNode reverseListHuiwen2(ListNode head) {
        return reverseHuiwen(head, head, null);
    }

    /*
     * 原：
     * 1 -> 2 -> 3 -> 5
     * ↑______________|
     * 反转：
     * 1 <- 2 <- 3 <- 5
     * |______________↑
     *
     * in        out
     * | 1      null ↑
     * | 2         1 |
     * | 3         2 |
     * | 4         3 |
     * | 5         4 |
     * ↓ null      5 |
     *
     * 过程举例：
     * 1 -> 2 -> 3          6 -> 5 -> 4
     *          ↑↓                   ↑↓
     *           4                    3
     * ====================================
     * 1 -> 2               6 -> 5 -> 4 -> 3
     *     ↑↓                             ↑↓
     *      3                              2
     * 参数：
     * out.next = in
     */
    public static ListNode reverseHuiwen(ListNode head, ListNode in, ListNode out) {
        if (in == null)
            return out;
        if (in.next == head) {
            in.next = out;
            return in;
        } else {
            ListNode node = reverseHuiwen(head, in.next, in);
            if (out == null)
                in.next = node;
            else
                in.next = out;
            return node;
        }

    }

}
