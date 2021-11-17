package com.loper.mine.core.arraysort;

import java.util.Arrays;

/**
 * @author liaonanzhou
 * @date 2021/9/28 16:58
 * @description 快速排序
 * 时间复杂度：O(nlogn)
 **/
public class KuaisuSort {

    public static void main(String[] args) {
        int[] arr = new int[]{5, 3, 6, 1, 2, 0, 9, 4};
        //doubleSort(arr, 0, arr.length - 1);
        singleSort(arr, 0, arr.length - 1);
    }

    /*
     * 双边循环法
     * pivot 基准节点
     * left  左节点
     * right 右节点
     *
     * 如：5, 3, 6, 1, 2, 0
     * [5, 3, 6, 1, 2, 0],当前轮：pivot = 0,left = 0,right = 5
     * [2, 3, 0, 1, 5, 6],当前轮：pivot = 0,left = 0,right = 4
     * [0, 1, 2, 3, 5, 6],当前轮：pivot = 0,left = 0,right = 2
     */
    private static int[] doubleSort(int[] arr, int pivot, int right) {
        if (pivot == right)
            return arr;

        int left = 0;
        System.out.println(Arrays.toString(arr) + ",当前轮：pivot = " + pivot + ",left = " + left + ",right = " + right);

        while (left != right) {
            // 大于或者等于right才左移，那么right右边的数列都大于等于pivot
            if (arr[right] >= arr[pivot])
                right--;
            else if (arr[left] <= arr[pivot]) {
                left++;
            }
            if (arr[left] > arr[pivot]) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }
        // pivot 节点和 left/right 节点呼唤
        int temp = arr[pivot];
        arr[pivot] = arr[left];
        arr[left] = temp;
        return doubleSort(arr, pivot, right);
    }

    /*
     * 单边循环法
     * pivot 基准节点
     * mark  mark节点
     * end   节点（右边界）
     */
    private static void singleSort(int[] arr, int start, int end) {
        if (start >= end)
            return;

        int mark = mark(arr, start, end);
        System.out.println(Arrays.toString(arr) + ":" + start + ":" + end + ":" + mark);

        if (mark == start) {
            start++;

            singleSort(arr, start, end);
        } else {
            int temp = arr[start];
            arr[start] = arr[mark];
            arr[mark] = temp;

            singleSort(arr, start, mark - 1);
            singleSort(arr, mark + 1, end);
        }

    }

    private static int mark(int[] arr, int start, int end) {
        int pivot, mark;
        mark = pivot = start;
        for (int i = start + 1; i <= end; i++) {
            if (arr[i] <= arr[pivot]) {
                mark++;

                int temp = arr[i];
                arr[i] = arr[mark];
                arr[mark] = temp;
            }
        }

        return mark;
    }


}
