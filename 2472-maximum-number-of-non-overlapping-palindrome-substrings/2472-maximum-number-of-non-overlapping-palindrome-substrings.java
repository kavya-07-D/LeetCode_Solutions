class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        int count = 0;
        int start = 0; // The starting index available for the next palindrome

        for (int i = 0; i < n; i++) {
            // Check if there is a valid palindrome of length k ending at i
            if (i - start + 1 >= k && isPalindrome(s, i - k + 1, i)) {
                count++;
                start = i + 1; // Move the available start pointer past this palindrome
                continue;
            }
            // Check if there is a valid palindrome of length k + 1 ending at i
            if (i - start + 1 >= k + 1 && isPalindrome(s, i - k, i)) {
                count++;
                start = i + 1; // Move the available start pointer past this palindrome
            }
        }
        
        return count;
    }

    // Helper method to check if the substring s[left...right] is a palindrome
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}