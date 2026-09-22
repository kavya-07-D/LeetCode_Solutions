import java.util.Arrays;

class Solution {
    private int[] prodTree;
    private int[][] countTree;
    private int kVal;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        kVal = k;
        
        prodTree = new int[4 * n + 1];
        countTree = new int[4 * n + 1][k];
        
        build(1, 0, n - 1, nums);
        
        int[] ans = new int[queries.length];
        int[] resCount = new int[k];
        int[] resProd = new int[1];
        
        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];
            
            update(1, 0, n - 1, index, value);
            
            Arrays.fill(resCount, 0);
            resProd[0] = 1;
            
            query(1, 0, n - 1, start, n - 1, resCount, resProd);
            
            ans[i] = resCount[x];
        }
        
        return ans;
    }
    
    private void build(int node, int start, int end, int[] nums) {
        if (start == end) {
            int v = nums[start] % kVal;
            prodTree[node] = v;
            countTree[node][v] = 1;
            return;
        }
        int mid = start + (end - start) / 2;
        build(2 * node, start, mid, nums);
        build(2 * node + 1, mid + 1, end, nums);
        mergeNodes(node, 2 * node, 2 * node + 1);
    }
    
    private void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            int v = val % kVal;
            prodTree[node] = v;
            for (int i = 0; i < kVal; i++) {
                countTree[node][i] = 0; 
            }
            countTree[node][v] = 1;
            return;
        }
        int mid = start + (end - start) / 2;
        if (idx <= mid) {
            update(2 * node, start, mid, idx, val);
        } else {
            update(2 * node + 1, mid + 1, end, idx, val);
        }
        mergeNodes(node, 2 * node, 2 * node + 1);
    }
  
    private void mergeNodes(int node, int left, int right) {
  
        prodTree[node] = (prodTree[left] * prodTree[right]) % kVal;
        
        for (int i = 0; i < kVal; i++) {
            countTree[node][i] = countTree[left][i];
        }
        
        for (int i = 0; i < kVal; i++) {
            int newProd = (prodTree[left] * i) % kVal;
            countTree[node][newProd] += countTree[right][i];
        }
    }
   
    private void query(int node, int start, int end, int l, int r, int[] resCount, int[] resProd) {
        if (r < start || end < l) return;
        
        if (l <= start && end <= r) {
            int[] tempCount = new int[kVal];
           
            for (int i = 0; i < kVal; i++) {
                tempCount[i] = resCount[i];
            }
            
            for (int i = 0; i < kVal; i++) {
                int newProd = (resProd[0] * i) % kVal;
                tempCount[newProd] += countTree[node][i];
            }
            for (int i = 0; i < kVal; i++) {
                resCount[i] = tempCount[i];
            }
            resProd[0] = (resProd[0] * prodTree[node]) % kVal;
            
            return;
        }
        
        int mid = start + (end - start) / 2;

        query(2 * node, start, mid, l, r, resCount, resProd);
        query(2 * node + 1, mid + 1, end, l, r, resCount, resProd);
    }
}