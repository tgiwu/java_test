package com.zhy.money;

import java.util.Scanner;

public class Money {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int n = in.nextInt();
            System.out.println(count(n));
        }
    }
    private static long count(int n) {
        if (n <= 0) return 0;
        long[] dp = new long[n + 1];
        dp[0] = 1;
        int[] coins = new int[]{1, 5, 10, 20, 50, 100};
        for (int coin : coins) {
            for (int j = coin; j <= n; j ++) {
                dp[j] = dp[j] + dp[j - coin];
            }
        }
        return dp[n];
    }
}
