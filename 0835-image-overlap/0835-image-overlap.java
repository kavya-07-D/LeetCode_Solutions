class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        List<int[]> list1 = new ArrayList<>();
        List<int[]> list2 = new ArrayList<>();

        // 1. Collect all coordinates where the value is 1 in both images
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) list1.add(new int[]{i, j});
                if (img2[i][j] == 1) list2.add(new int[]{i, j});
            }
        }

        // 2. Count the frequency of each translation vector (dx, dy)
        // Since indices range from 0 to n-1, the maximum difference is (n-1) to -(n-1).
        // We shift the matrix by 'n' to avoid negative indices.
        int[][] count = new int[2 * n][2 * n];
        int maxOverlap = 0;

        for (int[] p1 : list1) {
            for (int[] p2 : list2) {
                int dx = p2[0] - p1[0] + n;
                int dy = p2[1] - p1[1] + n;
                count[dx][dy]++;
                
                // Track the maximum overlap found so far
                maxOverlap = Math.max(maxOverlap, count[dx][dy]);
            }
        }

        return maxOverlap;
    }
}