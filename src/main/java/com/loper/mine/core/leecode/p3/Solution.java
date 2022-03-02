package com.loper.mine.core.leecode.p3;

/**
 * @author liaonanzhou
 * @date 2021/9/24 10:57
 * @description 接雨水
 **/
public class Solution {

    /*
     *           _
     * -         - -     -   -
     * -     -   - - - - - - -
     * - -   - - - - - - - - -
     * - - - - - - - - - - - -
     * 4 2 0 3 2 5 4 3 3 4 3 4
     * ^         ^       ^   ^
     * <p>
     * 1：求出最高挡板
     * 2：依次遍历求出最高挡板的左边所有挡板（上升趋势）
     * 3：依次遍历求出最高挡板的右边所有挡板（下降趋势）
     *
     * 所有挡板连起来将是一条抛物线
     */
    public static void main(String[] args) {
        int[] height = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int[] height2 = new int[]{4, 2, 0, 3, 2, 5};
        int[] height3 = new int[]{4, 2, 0, 3, 2, 5, 4, 3, 3, 4, 3, 4};
        System.out.println(trap(height3));
    }

    /*
     * 时间复杂度O（n）- O(n/2)
     */
    public static int trap(int[] height) {
        // 总共雨水量
        int count = 0, len = height.length;
        // 求最大值
        int max = 0, maxIdx = 0;
        for (int i = 0; i < len; i++) {
            int h = height[i];
            if (max <= h) {
                max = h;
                maxIdx = i;
            }
        }

        // 求最大值左边
        int lastMaxIdx2 = maxIdx;
        while (lastMaxIdx2 > 0) {
            int max2 = 0, maxIdx2 = 0;
            for (int i = 0; i < lastMaxIdx2; i++) {
                int h = height[i];
                if (max2 < h) {
                    max2 = h;
                    maxIdx2 = i;
                }
            }
            for (int i = maxIdx2 + 1; i < lastMaxIdx2; i++) {
                count += max2 - height[i];
            }
            lastMaxIdx2 = maxIdx2;
        }

        // 求最大值右边
        int lastMaxIdx3 = maxIdx;
        while (lastMaxIdx3 < len - 1) {
            int max3 = 0, maxIdx3 = 0;
            for (int i = lastMaxIdx3 + 1; i < len; i++) {
                int h = height[i];
                if (max3 <= h) {
                    max3 = h;
                    maxIdx3 = i;
                }
            }
            for (int i = lastMaxIdx3 + 1; i < maxIdx3; i++) {
                count += max3 - height[i];
            }
            lastMaxIdx3 = maxIdx3;
        }


        return count;

    }


}
