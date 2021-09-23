package com.loper.mine.core.leecode.p1;

import java.util.Arrays;

/**
 * @author liaonanzhou
 * @date 2021/9/22 16:13
 * @description {@link https://leetcode.com/problems/two-sum/}
 **/
public class Solution {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(twoSum(arr, 9)));
    }


    public static int[] twoSum(int[] nums, int target) {
        int[] solution = new int[2];
        int temp;
        for (int i = 0; i < nums.length; i++) {
            temp = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int num = temp + nums[j];
                if (num == target) {
                    solution[0] = i;
                    solution[1] = j;
                    return solution;
                }
            }
        }
        return solution;
    }

}
