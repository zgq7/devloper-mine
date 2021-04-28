package com.loper.mine.core.huaiwei;

import java.util.Scanner;

/**
 * @author liaonanzhou
 * @date 2021-03-07 10:27
 * @description 给定一个整形数组，判断数组中是否存在A=B+2C 的情况，存在则输出A B C，否则输出O
 * 华为测试用例全部通过
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (3 <= n && n <= 100) {
            int[] bom = new int[n];
            for (int i = 0; i < n; i++) {
                bom[i] = sc.nextInt();
            }

            sort(bom);

            math(bom);
        }

    }

    private static void math(int[] arr) {
        boolean ex = false;
        for (int i = 2; i < arr.length; i++) {
            int a = arr[i];
            for (int j = i - 1; j >= 0; j--) {
                int b = arr[j];
                if (a == b)
                    continue;
                for (int k = j - 1; k >= 0; k--) {
                    int c = arr[k];
                    if (a == c || b == c)
                        continue;
                    if (a == (b + 2 * c)) {
                        System.out.println(a + " " + b + " " + c);
                        ex = true;
                    }
                }
            }
        }

        if (!ex)
            System.out.println(0);
    }

    private static void sort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (arr[i] > arr[j]) {
                    int tm = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tm;
                }
            }
        }
    }

}
