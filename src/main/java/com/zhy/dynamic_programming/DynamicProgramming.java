package com.zhy.dynamic_programming;

/**
 * 动态规划原则：记住已经解决的子问题的结果
 *
 * */
public class DynamicProgramming {
    /**
     * 切割钢线问题,计算收益最大
     *
     * 子问题：每次只切一刀或不切
     * 收益可抽象为
     * r[n] = max(p[n], r[1] + r[n-1], r[2] + r[n-2]...r[n-1] + r[1])
     *
     * p[n] 对应不切割
     * 其余参数对应之后的n-1中切割方案
     *
     */

    //长度对应的价格
    private int[] p = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};

    /**
     *递归方式
     * 考虑每次解决两段问题，一段为不再切割，一段为待切割
     * 收益可抽象为 当1 《= i 《= n时
     * r[i] = max(p[i] + r[n - i])
     *
     **/
    private int cut(int n) {
        if (n == 0) return 0;

        int q = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            q = Math.max(q, p[i - 1] + cut(n - i));
        }
        return q;
    }

    /**
     * 备忘录式动态规划
     *
     * */
    private int cutMemory() {
        int[] r = new int[p.length + 1];
        for (int i = 0; i < r.length; i++) {
            r[i] = -1;
        }
        return cut(p.length, r);
    }
    private int cut(int n, int[] r) {
        //已经计算过
        if (r[n] >= 0 ) return r[n];

        int q = Integer.MIN_VALUE;
        if (n == 0) q =0;
        for (int i = 1; i <= n; i++) {
            q = Math.max(q, cut(n - i, r) + p[i - 1] );
        }
        r[n] = q;
        return q;
    }

    /**
     * 自底向上的动态规划
     *
     * */
    private int cutBottomToTop() {
        int[] r = new int[p.length + 1];
        //计算收益
        for (int i = 1; i < r.length; i ++) {
            int q = -1;
            //计算收益最大值
            for (int j = 1; j <= i; j++) {
                q = Math.max(q, p[j -1] + r[i - j]);
            }
            r[i] = q;
        }
        return r[p.length];
    }

    public static void main(String[] args) {
        DynamicProgramming dynamicProgramming = new DynamicProgramming();
        System.out.println("recursion cut 9 result: " + dynamicProgramming.cut(10));

        System.out.println("memory cut result: " + dynamicProgramming.cutMemory());

        System.out.println("bottom to top result: " + dynamicProgramming.cutBottomToTop());
    }



}
