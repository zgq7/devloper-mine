package com.loper.mine.core.arraysort;

import java.util.Arrays;

/**
 * @author liaonanzhou
 * @date 2021/9/28 13:46
 * @description 冒泡排序
 * 时间复杂度：O(n^2)
 **/
public class MaopaoSort {

    public static void main(String[] args) {
        int[] arr = new int[]{5, 3, 6, 1, 2};
        sort2(arr);
    }

    private static int[] sort1(int[] arr) {
        /*
         * 每次排序都将最大值放到末尾，下一轮的排序少比较一轮。
         * 如：5 3 6 1 2
         *    [3, 5, 1, 2, 6]
         *    [3, 1, 2, 5, 6]，第二轮对比的是3, 5, 1, 2,
         *    [1, 2, 3, 5, 6]，第三轮对比的是3, 1, 2
         *    [1, 2, 3, 5, 6]，第四轮对比的是1, 2
         *    [1, 2, 3, 5, 6]
         */
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                int temp;
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
        return arr;
    }

    private static int[] sort2(int[] arr) {
        /*
         * 优化版本：
         * 可以看到在sort1中提前就排好序了，不过还是每次都会去进行排序。
         * 那么我们在第四轮排序时就可以中断后续排序了。
         */
        for (int i = 0; i < arr.length; i++) {
            // 假设当前这一次排序前已经是排好序的数据了
            boolean sort = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                int temp;
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    sort = false;
                }
            }
            if (sort)
                break;
            System.out.println(Arrays.toString(arr));
        }
        return arr;
    }
}
