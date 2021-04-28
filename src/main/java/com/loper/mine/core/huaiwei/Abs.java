package com.loper.mine.core.huaiwei;

import java.util.Scanner;

/**
 * @author liaonanzhou
 * @date 2021-03-07 11:43
 * @description 给一串小写的字符串数组以“,”分开，查询出字符串中包含其他字符串字符的的最大长度，不存在则返回0；
 * 华为测试用例通过率：60%
 */
public class Abs {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] arr = sc.nextLine().split(",");
        int abs = 0;
        if (arr.length >= 2 && arr.length <= 100) {
            for (String str : arr) {
                if (str.length() > 50)
                    break;
            }
            for (int i = 0; i < arr.length - 1; i++) {
                String str = arr[i];
                int alen = str.length();

                for (int j = i + 1; j < arr.length; j++) {
                    String[] bom = arr[j].split("");
                    int blen = bom.length;
                    boolean contain = false;
                    for (String s : bom) {
                        if (str.contains(s)) {
                            contain = true;
                            break;
                        }
                    }

                    if (!contain) {
                        int sf = alen * blen;
                        if (sf > abs)
                            abs = sf;
                    }
                }


            }

        }

        System.out.println(abs);

    }
}
