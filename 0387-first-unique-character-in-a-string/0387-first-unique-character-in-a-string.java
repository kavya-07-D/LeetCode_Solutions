class Solution {
    public int firstUniqChar(String a) {
        for(int i=0;i<a.length();i++){
            char c=a.charAt(i);
            if (a.indexOf(c) == a.lastIndexOf(c)) {
                return i;
            }
        }
        return -1;
    }
}