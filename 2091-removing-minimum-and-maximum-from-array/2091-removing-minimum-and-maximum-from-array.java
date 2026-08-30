class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        if (n <= 2) return n; // If 1 or 2 elements, we have to remove all of them.

        int minIdx = 0;
        int maxIdx = 0;

        // Find the indices of the minimum and maximum elements
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        // Ensure 'i' is the smaller index and 'j' is the larger index
        int i = Math.min(minIdx, maxIdx);
        int j = Math.max(minIdx, maxIdx);

        // Scenario 1: Remove both from the front
        int deleteFront = j + 1;

        // Scenario 2: Remove both from the back
        int deleteBack = n - i;

        // Scenario 3: Remove from both ends
        int deleteBoth = (i + 1) + (n - j);

        // Return the minimum of the three scenarios
        return Math.min(deleteFront, Math.min(deleteBack, deleteBoth));
    }
}