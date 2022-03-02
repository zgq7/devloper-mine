package com.loper.mine.core.leecode.p2;


/**
 * @author liaonanzhou
 * @date 2021/9/27 14:11
 * @description 剑指 Offer II 077. 链表排序
 * https://leetcode-cn.com/problems/7WHec2/
 **/
public class Solution2 {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(6, n1);
        ListNode n3 = new ListNode(2, n2);
        ListNode n4 = new ListNode(5, n3);
        ListNode n5 = new ListNode(4, n4);
        ListNode n6 = new ListNode(9, n5);
        ListNode n = new ListNode(0, n6);

        sortList(n);
    }

    public static ListNode sortList(ListNode head) {
        return null;
    }
}
