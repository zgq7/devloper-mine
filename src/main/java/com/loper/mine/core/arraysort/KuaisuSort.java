package com.loper.mine.core.arraysort;

import java.util.Arrays;

/**
 * @author liaonanzhou
 * @date 2021/9/28 16:58
 * @description 快速排序
 * 时间复杂度：O(n·logn)
 **/
public class KuaisuSort {

    public static void main(String[] args) {
        int[] arr = new int[]{5, 3, 6, 1, 2, 0, 7, 4, 8, 11, 19, 15};
        //quickSort(arr, 0, arr.length - 1);
        //singleSort(arr, 0, arr.length - 1);
        System.out.println(zws(arr));
    }

    /*
     * 利用分治算法求数组中位数
     */
    private static int zws(int[] arr) {
        int zws = arr.length % 2 == 0 ? arr.length / 2 : (arr.length / 2) + 1;
        System.out.println("zws index = " + zws);
        int pivotIndex, start = 0, end = arr.length - 1;
        while (start != end) {
            pivotIndex = partition(arr, start, end);

            if (pivotIndex == start && pivotIndex != end - 1) {
                start++;
                continue;
            }
            if (pivotIndex == end && pivotIndex != start + 1) {
                end--;
                continue;
            }
            if (zws > pivotIndex)
                start = pivotIndex + 1;
            else if (zws < pivotIndex)
                end = pivotIndex;
            else
                break;
        }
        return arr[zws];
    }

    /*
     * 双边循环法 (分治算法)
     * pivot 基准节点值
     * left  左节点
     * right 右节点
     *
     * 如：5, 3, 6, 1, 2, 0
     * [5, 3, 6, 1, 2, 0],当前轮：pivot = arr[0],left = 0,right = 5
     * [2, 3, 0, 1, 5, 6],当前轮：pivot = arr[0],left = 0,right = 4
     * [0, 1, 2, 3, 5, 6],当前轮：pivot = arr[0],left = 0,right = 2
     */
    private static void quickSort(int[] arr, int start, int end) {
        if (start >= end)
            return;

        int pivotIndex = partition(arr, start, end);
        // 左边
        quickSort(arr, start, pivotIndex);
        // 右边
        quickSort(arr, pivotIndex + 1, end);
    }

    /*
     * 求出数组基准数所在下标
     */
    private static int partition(int[] arr, int start, int end) {
        // 假定基准节点pivot为最左边节点的值
        int pivot = arr[start];
        int left = start;
        int right = end;
        System.out.println(Arrays.toString(arr) + ",当前轮：pivot = " + pivot + ",left = " + left + ",right = " + right);

        while (left != right) {
            while (left < right && arr[right] > pivot)
                right--;

            while (left < right && arr[left] <= pivot)
                left++;

            if (left < right) {
                int temp = arr[right];
                arr[right] = arr[left];
                arr[left] = temp;
            }
        }
        arr[start] = arr[left];
        arr[left] = pivot;
        System.out.println("计算得到的pivot index = " + left);
        return left;
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
