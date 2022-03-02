package com.loper.mine.core.leecode.p2;

/**
 * @author liaonanzhou
 * @date 2021/9/22 16:18
 * @description {@link https://leetcode.com/problems/add-two-numbers/}
 **/
public class Solution {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(9);
        ListNode n2 = new ListNode(9, n1);
        ListNode n3 = new ListNode(9, n2);
        ListNode n4 = new ListNode(9, n3);
        ListNode n5 = new ListNode(9, n4);
        ListNode n6 = new ListNode(9, n5);
        ListNode n = new ListNode(9, n6);

        ListNode l1 = new ListNode(9);
        ListNode l2 = new ListNode(9, l1);
        ListNode l3 = new ListNode(9, l2);
        ListNode l = new ListNode(9, l3);

        ListNode merge = addTwoNumbers3(n, l);
        while (true) {
            if (merge != null) {
                System.out.print(merge.val);
                merge = merge.next;
            } else {
                System.out.println("\n");
                break;
            }
        }

    }

    /**
     * 我的 v1
     **/
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode inverse = null;
        while (true) {
            if (l1.val == -1 && l2.val == -1 && carry == 0) {
                break;
            }
            if (l1.val == -1)
                l1.val = 0;
            if (l2.val == -1)
                l2.val = 0;

            int sum = l1.val + l2.val + carry;
            carry = sum / 10;
            inverse = new ListNode(sum % 10, inverse);

            l1 = l1.next == null ? new ListNode(-1) : l1.next;
            l2 = l2.next == null ? new ListNode(-1) : l2.next;
        }

        ListNode along = null;
        while (inverse != null) {
            along = new ListNode(inverse.val, along);
            inverse = inverse.next;
        }

        return along;
    }

    /**
     * 我的 v2
     **/
    public static ListNode addTwoNumbers3(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode current = root;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int sum = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
            carry = sum / 10;

            current.next = new ListNode(sum % 10);
            current = current.next;

            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }

        if (carry >0){
            current.next = new ListNode(carry);
        }

        return root.next;
    }


    /**
     * 标准答案的
     **/
    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
}
