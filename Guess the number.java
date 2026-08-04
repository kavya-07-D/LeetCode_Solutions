class Solution {
    public int getMoneyAmount(int n) {
        int[][] dp = new int[n + 2][n + 2];

        // length is the size of the range
        for (int len = 2; len <= n; len++) {
            for (int l = 1; l + len - 1 <= n; l++) {
                int r = l + len - 1;
                dp[l][r] = Integer.MAX_VALUE;

                for (int x = l; x <= r; x++) {
                    int cost = x + Math.max(dp[l][x - 1], dp[x + 1][r]);
                    dp[l][r] = Math.min(dp[l][r], cost);
                }
            }
        }

        return dp[1][n];
    }
}