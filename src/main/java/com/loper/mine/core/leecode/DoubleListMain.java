package com.loper.mine.core.leecode;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liaonanzhou
 * @date 2022/5/7 10:37
 * @description
 **/
public class DoubleListMain {

    public static void main(String[] args) {
        Node node1 = new Node(1, null);
        Node node2 = new Node(2, node1);
        Node node3 = new Node(3, node2);
        Node node4 = new Node(4, node3);

        // 转为双向链表
        Node w = node1;
        Node q = null;
        Node p;
        for (p = w; p != null; p = p.next) {
            p.prev = q;
            q = p;
        }

        // 反转链表
        Node s = null;
        Node t = node1;
        Node u = null;
        while (t != null) {
            u = t.next;
            t.prev = u;
            t.next = s;
            s = t;
            t = u;
        }

        while (s != null) {
            System.out.println(s.val);
            s = s.next;
        }
    }

    @Setter
    @Getter
    private static class Node {
        private int val;
        private Node prev;
        private Node next;

        public Node(int val, Node prev) {
            this.val = val;
            //this.prev = prev;
            if (prev != null)
                prev.next = this;
        }
    }
}
