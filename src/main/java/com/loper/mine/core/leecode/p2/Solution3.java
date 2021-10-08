package com.loper.mine.core.leecode.p2;

/**
 * @author liaonanzhou
 * @date 2021/9/27 17:46
 * @description 1290. 二进制链表转整数
 * https://leetcode-cn.com/problems/convert-binary-number-in-a-linked-list-to-integer/
 **/
public class Solution3 {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(0, n1);
        ListNode n3 = new ListNode(1, n2);
        ListNode n4 = new ListNode(0, n3);
        ListNode n5 = new ListNode(1, n4);
        ListNode n6 = new ListNode(0, n5);
        ListNode n = new ListNode(1, n6);

        System.out.println(getDecimalValue(n));
    }

    public static int getDecimalValue(ListNode head) {
        int res = 0;
        while (head != null) {
            res <<= 1;
            res |= head.val;
            head = head.next;
        }
        return res;
    }

}
