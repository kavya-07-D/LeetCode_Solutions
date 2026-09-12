import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {
    public int[] maximumWeight(List<List<Integer>> intervalsList) {
        int n = intervalsList.size();
        int[][] intervals = new int[n][4];
        
        // Populate custom interval array with original indices
        for (int i = 0; i < n; i++) {
            intervals[i][0] = intervalsList.get(i).get(0); // start
            intervals[i][1] = intervalsList.get(i).get(1); // end
            intervals[i][2] = intervalsList.get(i).get(2); // weight
            intervals[i][3] = i;                           // original index
        }

        // Sort intervals strictly by right boundary (end time). 
        // If tied, sort by left boundary, then by original index.
        Arrays.sort(intervals, (a, b) -> {
            if (a[1] != b[1]) return Integer.compare(a[1], b[1]);
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[3], b[3]);
        });

        // dpWeight[c][i] = max score picking up to 'c' intervals from the first 'i' sorted intervals
        long[][] dpWeight = new long[5][n + 1];
        
        // dpIndices[c][i] = the lexicographically smallest list of indices achieving dpWeight[c][i]
        List<Integer>[][] dpIndices = new List[5][n + 1];
        
        for (int c = 0; c <= 4; c++) {
            for (int i = 0; i <= n; i++) {
                dpIndices[c][i] = new ArrayList<>();
            }
        }

        for (int i = 1; i <= n; i++) {
            int start = intervals[i - 1][0];
            int weight = intervals[i - 1][2];
            int origIdx = intervals[i - 1][3];

            // Find the 1-based index of the latest interval that finishes before the current one starts
            int prev = binarySearch(intervals, start);

            for (int c = 1; c <= 4; c++) {
                // Base state: Best sequence from 'c-1' intervals (effectively "At most c")
                long bestW = dpWeight[c - 1][i];
                List<Integer> bestId = dpIndices[c - 1][i];

                // Option 1: Skip current interval 'i', inherit from previous state of 'c' intervals
                long w1 = dpWeight[c][i - 1];
                List<Integer> id1 = dpIndices[c][i - 1];
                
                if (w1 > bestW || (w1 == bestW && isLexSmaller(id1, bestId))) {
                    bestW = w1;
                    bestId = id1;
                }

                // Option 2: Take current interval 'i', add it to best configuration of 'c-1' intervals up to 'prev'
                long w2 = dpWeight[c - 1][prev] + weight;
                List<Integer> id2 = new ArrayList<>(dpIndices[c - 1][prev]);
                id2.add(origIdx);
                Collections.sort(id2); // Sorting ensures lexicographical comparison holds true

                if (w2 > bestW || (w2 == bestW && isLexSmaller(id2, bestId))) {
                    bestW = w2;
                    bestId = id2;
                }

                // Save the optimal decision
                dpWeight[c][i] = bestW;
                dpIndices[c][i] = bestId;
            }
        }

        // Convert the best found configuration (up to 4 items) into the required int array
        List<Integer> resultList = dpIndices[4][n];
        int[] result = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }

    // Helper method to compare two index lists lexicographically
    private boolean isLexSmaller(List<Integer> list1, List<Integer> list2) {
        int len = Math.min(list1.size(), list2.size());
        for (int i = 0; i < len; i++) {
            int cmp = list1.get(i).compareTo(list2.get(i));
            if (cmp != 0) {
                return cmp < 0; // If list1 element is smaller, it's lexicographically smaller
            }
        }
        // If elements are identical up to the shortest length, the shorter list is smaller
        return list1.size() < list2.size();
    }

    // Helper method for finding the latest non-overlapping interval (returns a 1-based index)
    private int binarySearch(int[][] intervals, int targetStart) {
        int left = 0, right = intervals.length - 1;
        int res = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (intervals[mid][1] < targetStart) {
                res = mid;
                left = mid + 1; // Try to find an even later interval
            } else {
                right = mid - 1;
            }
        }
        return res + 1; 
    }
}