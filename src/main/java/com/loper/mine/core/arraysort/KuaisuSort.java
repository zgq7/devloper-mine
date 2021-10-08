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
     * mark  节点
     * right 节点（右边界）
     */
    private static int[] singleSort(int[] arr, int mark, int right) {
        System.out.println(Arrays.toString(arr) + ",当前轮：mark = " + mark + ",right = " + right);
        if (mark == right) {
            return arr;
        }

        int pivot = mark;
        int currentMark = mark;
        for (int i = pivot + 1; i <= right; i++) {
            if (arr[i] <= arr[pivot]) {
                mark++;

                int temp = arr[i];
                arr[i] = arr[mark];
                arr[mark] = temp;
            }
        }

        if (currentMark == mark && mark < arr.length) {
            mark++;
        } else {
            int temp = arr[mark];
            arr[mark] = arr[pivot];
            arr[pivot] = temp;

            // 左边
            arr = singleSort(arr, currentMark, mark - 1);
        }
        // 右边
        arr = singleSort(arr, mark, right);

        return arr;
    }


}
