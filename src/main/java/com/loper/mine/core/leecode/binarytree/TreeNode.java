package com.loper.mine.core.leecode.binarytree;

/**
 * @author liaonanzhou
 * @date 2022/4/2 18:33
 * @description
 **/
public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
