class Solution {
    private int[][] memo;
    private int[] suffixSum;

    public int stoneGameII(int[] piles) {
        int n = piles.length;
        memo = new int[n][n + 1];
        suffixSum = new int[n];

        // Compute suffix sums for quick range sum lookup
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }

        return dp(0, 1, piles);
    }

    private int dp(int i, int M, int[] piles) {
        int n = piles.length;

        // Base case: Beyond array bounds
        if (i >= n) return 0;

        // Best case: Current player can take all remaining piles
        if (i + 2 * M >= n) {
            return suffixSum[i];
        }

        // Return cached result if available
        if (memo[i][M] != 0) {
            return memo[i][M];
        }

        int maxStones = 0;
        for (int X = 1; X <= 2 * M; X++) {
            int nextM = Math.max(M, X);
            // Current player's score = Total remaining stones - Next player's optimal score
            int currentStones = suffixSum[i] - dp(i + X, nextM, piles);
            maxStones = Math.max(maxStones, currentStones);
        }

        return memo[i][M] = maxStones;
    }
}