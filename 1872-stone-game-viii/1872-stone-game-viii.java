class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        
        // Step 1: Compute prefix sums in-place or dynamically
        long[] prefixSum = new long[n];
        prefixSum[0] = stones[0];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + stones[i];
        }

        // Step 2: Base case - player taking all stones gets prefixSum[n - 1]
        long ans = prefixSum[n - 1];

        // Step 3: Iterate backwards from second to last stone down to index 1
        for (int i = n - 2; i >= 1; i--) {
            ans = Math.max(ans, prefixSum[i] - ans);
        }

        return(int)ans;
    }
}