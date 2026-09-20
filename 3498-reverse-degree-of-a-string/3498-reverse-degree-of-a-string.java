class Solution {
    public int reverseDegree(String s) {
        int totalDegree = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // Calculate the reversed alphabet index ('a' = 26, 'b' = 25, ..., 'z' = 1)
            int reversedCharValue = 26 - (s.charAt(i) - 'a');
            
            // Calculate the 1-indexed position in the string
            int position = i + 1;
            
            // Add the product to the total sum
            totalDegree += reversedCharValue * position;
        }
        
        return totalDegree;
    }
}