package com.loper.mine.core.huaiwei;

import java.util.Scanner;

/**
 * @author liaonanzhou
 * @date 2021-03-07 12:25
 * @description 贪吃蛇游戏
 */
public class Snake {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 记录走向
        byte[] bt = new byte[3];
        for (int i = 0; i < bt.length; i++) {
            bt[i] = sc.nextByte();
        }

        // 构建矩阵
        int[] nm = new int[2];
        Scanner sc2 = new Scanner(System.in);
        for (int i = 0; i < 2; i++) {
            nm[i] = sc2.nextInt();
        }

        byte[][] aByte = new byte[nm[0]][nm[1]];
        Scanner sc3;
        for (int i = 0; i < nm[0]; i++) {
            for (int j = 0; j < nm[1]; j++) {
                aByte[i][j] = sc.nextByte();
            }
        }


        System.out.println(aByte);

    }
}
